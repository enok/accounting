package br.com.accounting.core.service;

import br.com.accounting.core.entity.Order;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.filter.Duplicates;
import br.com.accounting.core.filter.Filtro;
import br.com.accounting.core.ordering.Ordem;

import java.util.List;

public interface GenericService<T> {
    Long salvar(T entity) throws ServiceException;

    void atualizar(List<T> oldEntitiesList, List<T> newEntitiesList) throws ServiceException;

    List<T> buscarRegistros() throws ServiceException;

    List<T> filtrar(Filtro filtro, Duplicates duplicates, List<T> entities) throws ServiceException;

    List<T> filtrar(Filtro filtro, List<T> entitys) throws ServiceException;

    List<T> filtrar(Filtro filtro, Duplicates duplicates) throws ServiceException;

    List<T> filtrar(Filtro filtro) throws ServiceException;

    T filtrarSingle(Filtro filtro, List<T> entitys) throws ServiceException;

    List<T> ordenar(Ordem ordem, Order order, List<T> entities) throws ServiceException;

    List<T> ordenar(Ordem ordem, Order order) throws ServiceException;
}
