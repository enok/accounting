package br.com.accounting.core.service;

import br.com.accounting.core.exception.RepositoryException;
import br.com.accounting.core.exception.StoreException;

import java.util.Map;

public interface HistoricoService {
    Long salvar(String metodo, Map<String, Object> parametros) throws StoreException, RepositoryException;
}
