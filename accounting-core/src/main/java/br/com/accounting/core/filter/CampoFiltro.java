package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Order;

import java.util.List;

public interface CampoFiltro<E, T> {
    List<E> filtrar(List<T> entities);

    List<E> ordenar(List<T> entities, Order order);
}
