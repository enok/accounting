package br.com.accounting.core.service;

import br.com.accounting.core.entity.Order;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.filter.Duplicates;
import br.com.accounting.core.filter.Filtro;
import br.com.accounting.core.ordering.CampoOrdem;

import java.util.List;

public interface GenericService<T> {
    void salvar(T entity) throws ServiceException;

    List<T> buscarRegistros() throws ServiceException;

    List<T> filtrar(Filtro filtro, Duplicates duplicates, List<T> entities) throws ServiceException;

    List<T> filtrar(Filtro filtro, Duplicates duplicates) throws ServiceException;

    List<T> ordenar(CampoOrdem campoOrdem, Order order, List<T> entities) throws ServiceException;

    List<T> ordenar(CampoOrdem campoOrdem, Order order) throws ServiceException;
}
