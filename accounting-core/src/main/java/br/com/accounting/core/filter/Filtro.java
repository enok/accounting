package br.com.accounting.core.filter;

import java.util.List;

public interface Filtro<E, T> {
    List<E> filtrar(List<T> entities, Duplicates duplicates);
}
