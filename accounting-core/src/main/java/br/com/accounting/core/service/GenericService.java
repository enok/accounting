package br.com.accounting.core.service;

import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.exception.StoreException;

import java.text.ParseException;
import java.util.List;

public interface GenericService<E> {
    Long salvar(E entity) throws StoreException, ServiceException;

    void atualizar(E entity) throws StoreException, ServiceException;

    void deletar(E entity) throws StoreException, ServiceException;

    E buscarPorCodigo(Long codigo) throws StoreException, ParseException;

    List<E> buscarTodas() throws StoreException, ParseException;
}
