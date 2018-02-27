package br.com.accounting.core.ordering;

import br.com.accounting.core.entity.Order;

import java.util.List;

public interface Ordem<E, T> {
    List<E> ordenar(List<T> entities, Order order);
}
