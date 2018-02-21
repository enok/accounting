package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Order;

import java.util.List;

public interface CampoFiltro<T> {
    List<T> filtrar(List<T> entities);

    List<T> ordenar(List<T> entities, Order order);
}
