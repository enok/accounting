package br.com.accounting.core.service;

import br.com.accounting.core.entity.Order;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.filter.CampoFiltro;
import br.com.accounting.core.ordering.CampoOrdem;

import java.util.List;

public interface GenericService<T> {
    void salvar(T entity) throws ServiceException;

    List<T> buscarRegistros() throws ServiceException;

    List<T> filtrar(CampoFiltro campoFiltro, List<T> entities) throws ServiceException;

    List<T> filtrar(CampoFiltro campoFiltro) throws ServiceException;

    List<T> ordenar(CampoOrdem campoOrdem, List<T> entities, Order order) throws ServiceException;

    List<T> ordenar(CampoOrdem campoOrdem, Order order) throws ServiceException;
}
