package br.com.accounting.core.service;

import br.com.accounting.core.entity.Local;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.exception.StoreException;

public interface LocalService extends GenericService<Local> {
    Local buscarPorNome(String nome) throws StoreException, ServiceException;
}
