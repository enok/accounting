package br.com.accounting.commons.service.impl;

import br.com.accounting.commons.annotation.History;
import br.com.accounting.commons.dto.EntityDTO;
import br.com.accounting.commons.entity.Entity;
import br.com.accounting.commons.exception.*;
import br.com.accounting.commons.factory.GenericDTOFactory;
import br.com.accounting.commons.service.GenericService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.util.CollectionUtils.isEmpty;

public abstract class GenericAbstractBusiness<D, E> {
    protected static final String msg = "O campo %s é obrigatório.";

    protected GenericService service;
    protected GenericDTOFactory<D, E> dtoFactory;

    public GenericAbstractBusiness(final GenericService genericService, final GenericDTOFactory dtoFactory) {
        this.service = genericService;
        this.dtoFactory = dtoFactory;
    }

    protected abstract void validarEntrada(D dto, List<String> erros) throws MissingFieldException, StoreException, ParseException, CreateException;

    protected abstract void validarEntradaUpdate(D dto, E entity, List<String> erros) throws ValidationException;

    protected abstract void validaRegistroDuplicado(E entity) throws StoreException, ParseException, DuplicatedRegistryException;

    protected abstract E criarEntity(D entity) throws ValidationException;

    protected abstract E criarEntity(D dto, E entityBuscado) throws ValidationException;

    @History
    public List<Long> criar(final D dto) throws ValidationException, StoreException, GenericException {
        try {
            final List<String> erros = new ArrayList<>();

            validarEntrada(dto, erros);

            E entity = criarEntity(dto);
            List<Long> codigos = new ArrayList<>();

            validaRegistroDuplicado(entity);
            codigos.add(service.salvar(entity));

            return codigos;
        }
        catch (ValidationException e) {
            throw e;
        }
        catch (StoreException e) {
            throw new StoreException("Erro de persistência ao salvar.", e);
        }
        catch (Exception e) {
            throw new GenericException(e);
        }
    }

    @History
    public void atualizar(final D dto) throws StoreException, BusinessException, GenericException {
        try {
            E entityBuscado = validarUpdate(dto);

            E entity = criarEntity(dto, entityBuscado);
            service.atualizar(entity);
        }
        catch (StoreException e) {
            throw new StoreException("Erro de persistência ao atualizar.", e);
        }
        catch (ValidationException e) {
            throw e;
        }
        catch (BusinessException e) {
            throw e;
        }
        catch (Exception e) {
            throw new GenericException(e);
        }
    }

    @History
    public void excluir(final D dto) throws BusinessException, StoreException, GenericException {
        try {
            E entity = criarEntity(dto);
            Long codigo = ((Entity) entity).getCodigo();
            E entityBuscada = (E) service.buscarPorCodigo(codigo);
            if (entityBuscada == null) {
                throw new BusinessException("Registro inexistente.");
            }
            service.deletar(entity);
        }
        catch (ValidationException e) {
            throw e;
        }
        catch (StoreException e) {
            throw new StoreException("Erro de persistência ao excluir.", e);
        }
        catch (BusinessException e) {
            throw e;
        }
        catch (Exception e) {
            throw new GenericException(e);
        }
    }

    public D buscarPorCodigo(final Long codigo) throws StoreException, BusinessException, GenericException {
        try {
            E entity = (E) service.buscarPorCodigo(codigo);
            if (entity == null) {
                throw new BusinessException("Registro inexistente.");
            }
            return criarDTO(dtoFactory, entity);
        }
        catch (StoreException e) {
            throw new StoreException("Erro de persistência ao buscar por código.", e);
        }
        catch (BusinessException e) {
            throw e;
        }
        catch (Exception e) {
            throw new GenericException(e);
        }
    }

    public List<D> buscarTodas() throws StoreException, GenericException {
        try {
            List<E> entities = service.buscarTodas();
            return criarListaDTO(dtoFactory, entities);
        }
        catch (StoreException e) {
            throw new StoreException("Erro de persistência ao buscar tudo.", e);
        }
        catch (Exception e) {
            throw new GenericException(e);
        }
    }

    public D criarDTO(final GenericDTOFactory genericDTOFactory, final E entity) {
        return (D) genericDTOFactory
                .begin()
                .preencherCampos(entity)
                .build();
    }

    protected List<D> criarListaDTO(final GenericDTOFactory genericDTOFactory, final List<E> entities) {
        List<D> entityesDTO = new ArrayList<>();
        for (E entity : entities) {
            entityesDTO.add(criarDTO(genericDTOFactory, entity));
        }
        return entityesDTO;
    }

    protected void conferirErrosCamposObrigatorios(final List<String> erros) throws MissingFieldException {
        if (!isEmpty(erros)) {
            StringBuilder builder = new StringBuilder();
            for (String erro : erros) {
                builder.append(erro);
            }
            throw new MissingFieldException(erros, builder.toString());
        }
    }

    protected void conferirErrosCreate(List<String> erros) throws CreateException {
        if (!isEmpty(erros)) {
            StringBuilder builder = new StringBuilder();
            for (String erro : erros) {
                builder.append(erro);
            }
            throw new CreateException(erros, builder.toString());
        }
    }

    protected void conferirErrosUpdate(final List<String> erros) throws ValidationException {
        if (!isEmpty(erros)) {
            StringBuilder builder = new StringBuilder();
            for (String erro : erros) {
                builder.append(erro);
            }
            throw new ValidationException(erros, builder.toString());
        }
    }

    protected void conferirValorBooleano(List<String> errosCreate, String valorBooleano, String campo) {
        if (!("S".equals(valorBooleano) || "N".equals(valorBooleano))) {
            errosCreate.add(String.format("O valor do campo %s é diferente de 'S' ou 'N'.", campo));
        }
    }

    protected E validarUpdate(D dto) throws StoreException, ParseException, BusinessException {
        final List<String> erros = new ArrayList<>();

        validarEntrada(dto, erros);

        String codigoString = ((EntityDTO) dto).getCodigo();
        Long codigo = null;
        if (!isBlank(codigoString)) {
            codigo = Long.parseLong(codigoString);
        }
        E entityBuscado = (E) service.buscarPorCodigo(codigo);

        if (entityBuscado == null) {
            throw new BusinessException("Registro inexistente.");
        }

        validarEntradaUpdate(dto, entityBuscado, erros);

        return entityBuscado;
    }
}
