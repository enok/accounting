package br.com.accounting.core.service;

import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.exception.StoreException;

public interface GrupoService extends GenericService<Grupo> {
    Grupo buscarPorNome(String nome) throws ServiceException, StoreException;
}
