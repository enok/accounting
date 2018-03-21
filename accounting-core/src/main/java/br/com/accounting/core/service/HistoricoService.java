package br.com.accounting.core.service;

import br.com.accounting.core.exception.ServiceException;

import java.util.Map;

public interface HistoricoService {
    Long salvar(String metodo, Map<String, Object> parametros) throws ServiceException;
}
