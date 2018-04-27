package br.com.accounting.core.service;

import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.exception.StoreException;

import java.util.List;

public interface GenericService<E> {
    Long salvar(E entity) throws StoreException, ServiceException;

    void atualizar(E entity) throws ServiceException;

    void deletar(E entity) throws ServiceException;

    E buscarPorCodigo(Long codigo) throws ServiceException, StoreException;

    List<E> buscarTodas() throws ServiceException, StoreException;
}
