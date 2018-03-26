package br.com.accounting.business.service;

import br.com.accounting.business.exception.DuplicatedRegistryException;
import br.com.accounting.business.exception.MissingFieldException;
import br.com.accounting.business.factory.GenericDTOFactory;
import br.com.accounting.core.exception.ServiceException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

public interface GenericBusiness<D, E> {
    void validarEntrada(final D dto, final List<String> erros, final boolean atualizacao) throws MissingFieldException;

    void validaRegistroDuplicado(E entity) throws ServiceException, DuplicatedRegistryException;

    default D criarDTOEntity(final GenericDTOFactory genericDTOFactory, final E entity) {
        return (D) genericDTOFactory
                .begin()
                .preencherCampos(entity)
                .build();
    }

    default void conferirErros(final List<String> erros) throws MissingFieldException {
        if (!isEmpty(erros)) {
            StringBuilder builder = new StringBuilder();
            for (String erro : erros) {
                builder.append("\n\t").append(erro);
            }
            throw new MissingFieldException(erros, builder.toString());
        }
    }

    default List<D> criarListaEntitiesDTO(final GenericDTOFactory genericDTOFactory, final List<E> entities) {
        List<D> entityesDTO = new ArrayList<>();
        for (E entity : entities) {
            entityesDTO.add(criarDTOEntity(genericDTOFactory, entity));
        }
        return entityesDTO;
    }

    E criarEntity(final D entity) throws ParseException;
}
