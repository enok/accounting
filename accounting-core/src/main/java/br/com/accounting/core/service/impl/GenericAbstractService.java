package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Entity;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.core.exception.RepositoryException;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.repository.GenericRepository;

import java.util.List;

public abstract class GenericAbstractService<E> {
    protected GenericRepository repository;

    public GenericAbstractService(GenericRepository repository) {
        this.repository = repository;
    }

    public Long salvar(final E entity) throws StoreException, ServiceException {
        try {
            setarProximoCodigo(entity);
            repository.salvar(entity);
        }
        catch (StoreException e) {
            throw e;
        }
        catch (Exception e) {
            String message = "Não foi possível salvar.";
            throw new ServiceException(message, e);
        }
        return ((Entity) entity).getCodigo();
    }

    public void atualizar(final E entity) throws ServiceException {
        try {
            repository.atualizar(entity);
        }
        catch (Exception e) {
            String message = "Não foi possível atualizar.";
            throw new ServiceException(message, e);
        }
    }

    public void deletar(final E entity) throws ServiceException {
        try {
            repository.deletar(entity);
        }
        catch (Exception e) {
            String message = "Não foi possível deletar.";
            throw new ServiceException(message, e);
        }
    }

    public E buscarPorCodigo(final Long codigo) throws ServiceException {
        try {
            List<E> registros = repository.buscarRegistros();
            return (E) repository.filtrarCodigo(registros, codigo);
        }
        catch (Exception e) {
            String message = "Não foi possível buscar o registro por código.";
            throw new ServiceException(message, e);
        }
    }

    public List<E> buscarTodas() throws ServiceException {
        try {
            List<E> entities = repository.buscarRegistros();
            ordenarTodas(entities);
            return entities;
        }
        catch (Exception e) {
            String message = "Não foi possível buscar todas.";
            throw new ServiceException(message, e);
        }
    }

    public abstract void ordenarTodas(List<E> entities);

    protected void setarProximoCodigo(final E entity) throws StoreException, RepositoryException {
        Long proximoCodigo = repository.proximoCodigo();
        repository.incrementarCodigo(proximoCodigo);
        ((Entity) entity).setCodigo(proximoCodigo);
    }
}
