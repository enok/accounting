package br.com.accounting.core.service;

import br.com.accounting.core.entity.Entity;
import br.com.accounting.core.exception.RepositoryException;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.repository.GenericRepository;

import java.util.List;

public interface GenericService<T> {
    default void setarProximoCodigo(final GenericRepository repository, final T entity) throws RepositoryException {
        Long proximoCodigo = repository.proximoCodigo();
        repository.incrementarCodigo(proximoCodigo);
        ((Entity) entity).setCodigo(proximoCodigo);
    }

    default T buscarPorCodigo(final GenericRepository repository, final Long codigo) throws ServiceException {
        try {
            List<T> registros = repository.buscarRegistros();
            return (T) repository.filtrarCodigo(registros, codigo);
        }
        catch (Exception e) {
            String message = "Não foi possível buscar o registro por código.";
            throw new ServiceException(message, e);
        }
    }
}
