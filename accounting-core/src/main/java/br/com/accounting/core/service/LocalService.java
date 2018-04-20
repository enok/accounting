package br.com.accounting.core.service;

import br.com.accounting.core.entity.Local;
import br.com.accounting.core.exception.ServiceException;

public interface LocalService extends GenericService<Local> {
    Local buscarPorNome(String nome) throws ServiceException;
}
