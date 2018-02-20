package br.com.accounting.core.filter;

import java.util.List;

public interface CampoFiltro<T> {
    List<T> filtrar(List<T> entities);
}
