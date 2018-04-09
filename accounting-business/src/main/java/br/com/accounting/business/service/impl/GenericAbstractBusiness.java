package br.com.accounting.business.service.impl;

import br.com.accounting.business.annotation.History;
import br.com.accounting.business.dto.EntityDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.DuplicatedRegistryException;
import br.com.accounting.business.exception.MissingFieldException;
import br.com.accounting.business.exception.UpdateException;
import br.com.accounting.business.factory.GenericDTOFactory;
import br.com.accounting.core.entity.Entity;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.service.GenericService;

import java.text.ParseException;
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

    public abstract void validarEntrada(D dto, List<String> erros) throws MissingFieldException;

    public abstract void validarEntradaUpdate(D dto, E entity, List<String> erros) throws MissingFieldException, UpdateException;

    public abstract void validaRegistroDuplicado(E entity) throws ServiceException, DuplicatedRegistryException;

    public abstract List<E> criarEntities(D entity) throws ParseException;

    @History
    public List<Long> criar(final D dto) throws BusinessException {
        try {
            final List<String> erros = new ArrayList<>();

            validarEntrada(dto, erros);

            List<E> entities = criarEntities(dto);

            List<Long> codigos = new ArrayList<>();
            for (E entity : entities) {
                validaRegistroDuplicado(entity);
                codigos.add(service.salvar(entity));
            }
            return codigos;
        }
        catch (Exception e) {
            String message = "Não foi possível criar.";
            throw new BusinessException(message, e);
        }
    }

    @History
    public void atualizar(final D dto) throws BusinessException {
        try {
            final List<String> erros = new ArrayList<>();

            validarEntrada(dto, erros);

            String codigoString = ((EntityDTO) dto).getCodigo();
            Long codigo = null;
            if (!isBlank(codigoString)) {
                codigo = Long.parseLong(codigoString);
            }
            E entity = (E) service.buscarPorCodigo(codigo);

            validarEntradaUpdate(dto, entity, erros);

            List<E> entities = criarEntities(dto);
            for (E e : entities) {
                service.atualizar(e);
            }
        }
        catch (Exception e) {
            String message = "Não foi possível atualizar.";
            throw new BusinessException(message, e);
        }
    }

    @History
    public void excluir(final D dto) throws BusinessException {
        try {
            List<E> entities = criarEntities(dto);
            for (E entity : entities) {
                service.deletar(entity);
            }
        }
        catch (Exception e) {
            String message = "Não foi possível excluir.";
            throw new BusinessException(message, e);
        }
    }

    public D buscarPorId(final Long codigo) throws BusinessException {
        try {
            E entity = (E) service.buscarPorCodigo(codigo);
            return criarDTOEntity(dtoFactory, entity);
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
            entitiesDTO = criarListaEntitiesDTO(dtoFactory, entities);
        }
        catch (Exception e) {
            String message = "Não foi possível buscar todas.";
            throw new BusinessException(message, e);
        }
        return entitiesDTO;
    }

    public D criarDTOEntity(final GenericDTOFactory genericDTOFactory, final E entity) {
        return (D) genericDTOFactory
                .begin()
                .preencherCampos(entity)
                .build();
    }

    protected List<D> criarListaEntitiesDTO(final GenericDTOFactory genericDTOFactory, final List<E> entities) {
        List<D> entityesDTO = new ArrayList<>();
        for (E entity : entities) {
            entityesDTO.add(criarDTOEntity(genericDTOFactory, entity));
        }
        return entityesDTO;
    }

    protected void conferirErros(final List<String> erros) throws MissingFieldException {
        if (!isEmpty(erros)) {
            StringBuilder builder = new StringBuilder();
            for (String erro : erros) {
                builder.append("\n\t").append(erro);
            }
            throw new MissingFieldException(erros, builder.toString());
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
            errosUpdate.add("O código não pode ser alterado.");
        }
    }
}
