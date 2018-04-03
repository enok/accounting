package br.com.accounting.business.service.impl;

import br.com.accounting.business.annotation.History;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.DuplicatedRegistryException;
import br.com.accounting.business.exception.MissingFieldException;
import br.com.accounting.business.factory.ContabilidadeDTOFactory;
import br.com.accounting.business.factory.GenericDTOFactory;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.service.GenericService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

public abstract class GenericAbstractBusiness<D, E> {
    protected GenericService service;
    protected GenericDTOFactory<D, E> dtoFactory;

    public GenericAbstractBusiness(final GenericService genericService, final GenericDTOFactory dtoFactory) {
        this.service = genericService;
        this.dtoFactory = dtoFactory;
    }

    public abstract void validarEntrada(D dto, List<String> erros, boolean atualizacao) throws MissingFieldException;

    public abstract void validaRegistroDuplicado(E entity) throws ServiceException, DuplicatedRegistryException;

    public abstract E criarEntity(D entity) throws ParseException;

    @History
    public Long criar(final D dto) throws BusinessException {
        try {
            final List<String> erros = new ArrayList<>();

            validarEntrada(dto, erros, false);

            E entity = criarEntity(dto);

            validaRegistroDuplicado(entity);

            return service.salvar(entity);
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

            validarEntrada(dto, erros, true);

            E entity = criarEntity(dto);

            service.atualizar(entity);
        }
        catch (Exception e) {
            String message = "Não foi possível atualizar.";
            throw new BusinessException(message, e);
        }
    }

    @History
    public void excluir(final D dto) throws BusinessException {
        try {
            E entity = criarEntity(dto);
            service.deletar(entity);
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

    public void conferirErros(final List<String> erros) throws MissingFieldException {
        if (!isEmpty(erros)) {
            StringBuilder builder = new StringBuilder();
            for (String erro : erros) {
                builder.append("\n\t").append(erro);
            }
            throw new MissingFieldException(erros, builder.toString());
        }
    }

    private List<D> criarListaEntitiesDTO(final GenericDTOFactory genericDTOFactory, final List<E> entities) {
        List<D> entityesDTO = new ArrayList<>();
        for (E entity : entities) {
            entityesDTO.add(criarDTOEntity(genericDTOFactory, entity));
        }
        return entityesDTO;
    }
}
