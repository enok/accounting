package br.com.accounting.business.service.impl;

import br.com.accounting.business.annotation.History;
import br.com.accounting.business.dto.EntityDTO;
import br.com.accounting.business.exception.*;
import br.com.accounting.business.factory.GenericDTOFactory;
import br.com.accounting.core.entity.Entity;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.core.service.GenericService;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
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

    protected abstract void validarEntrada(D dto, List<String> erros) throws MissingFieldException, CreateException;

    protected abstract void validarEntradaUpdate(D dto, E entity, List<String> erros) throws MissingFieldException, UpdateException;

    protected abstract void validaRegistroDuplicado(E entity) throws StoreException, DuplicatedRegistryException, ServiceException;

    protected abstract E criarEntity(D entity) throws ValidationException;

    protected abstract E criarEntity(D dto, E entityBuscado) throws ValidationException;

    @History
    public List<Long> criar(final D dto) throws StoreException, BusinessException, GenericException {
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
            throw e;
        }
        catch (ServiceException e) {
            throw new BusinessException(e);
        }
        catch (BusinessException e) {
            throw e;
        }
        catch (Exception e) {
            throw new GenericException(e);
        }
    }

    @History
    public void atualizar(final D dto) throws StoreException, BusinessException {
        try {
            final List<String> erros = new ArrayList<>();

            validarEntrada(dto, erros);

            String codigoString = ((EntityDTO) dto).getCodigo();
            Long codigo = null;
            if (!isBlank(codigoString)) {
                codigo = Long.parseLong(codigoString);
            }
            E entityBuscado = (E) service.buscarPorCodigo(codigo);

            validarEntradaUpdate(dto, entityBuscado, erros);

            E entity = criarEntity(dto, entityBuscado);
            service.atualizar(entity);
        }
        catch (StoreException e) {
            throw e;
        }
        catch (Exception e) {
            String message = "Não foi possível atualizar.";
            throw new BusinessException(message, e);
        }
    }

    @History
    public void excluir(final D dto) throws StoreException, BusinessException {
        try {
            E entity = criarEntity(dto);
            service.deletar(entity);
        }
        catch (StoreException e) {
            throw e;
        }
        catch (Exception e) {
            String message = "Não foi possível excluir.";
            throw new BusinessException(message, e);
        }
    }

    public D buscarPorId(final Long codigo) throws BusinessException {
        try {
            E entity = (E) service.buscarPorCodigo(codigo);
            return criarDTO(dtoFactory, entity);
        }
        catch (Exception e) {
            String message = "Não foi possível buscar por id.";
            throw new BusinessException(message, e);
        }
    }

    public List<D> buscarTodas() throws BusinessException {
        List<D> entitiesDTO;
        try {
            List<E> entities = service.buscarTodas();
            entitiesDTO = criarListaDTO(dtoFactory, entities);
        }
        catch (Exception e) {
            String message = "Não foi possível buscar todas.";
            throw new BusinessException(message, e);
        }
        return entitiesDTO;
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
                builder.append("\n\t").append(erro);
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

    protected void conferirErrosUpdate(final List<String> erros) throws UpdateException {
        if (!isEmpty(erros)) {
            StringBuilder builder = new StringBuilder();
            for (String erro : erros) {
                builder.append("\n\t").append(erro);
            }
            throw new UpdateException(erros, builder.toString());
        }
    }

    protected boolean codigosDiferentes(final String codigoDTO, final Long codigoEntity) {
        return !codigoDTO.equals(codigoEntity.toString());
    }

    protected void conferirCodigo(D dto, List<String> erros) {
        if (isBlank(((EntityDTO) dto).getCodigo())) {
            erros.add(format(msg, "código"));
        }
    }

    protected void conferirCodigoAlterado(D dto, E entity, List<String> errosUpdate) {
        if (entity == null || codigosDiferentes(((EntityDTO) dto).getCodigo(), ((Entity) entity).getCodigo())) {
            errosUpdate.add("O campo código não pode ser alterado.");
        }
    }
}
