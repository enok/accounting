package br.com.accounting.commons.service;

import br.com.accounting.commons.exception.RepositoryException;
import br.com.accounting.commons.exception.StoreException;

import java.util.Map;

public interface HistoricoService {
    Long salvar(String metodo, Map<String, Object> parametros) throws StoreException, RepositoryException;
}
