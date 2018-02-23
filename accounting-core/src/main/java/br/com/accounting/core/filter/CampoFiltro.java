package br.com.accounting.core.filter;

import java.util.List;

public interface CampoFiltro<E, T> {
    List<E> filtrar(List<T> entities);
}
