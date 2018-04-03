package br.com.accounting.core.service;

import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.exception.ServiceException;

public interface GrupoService extends GenericService<Grupo> {
    Grupo buscarPorNome(String nome) throws ServiceException;
}
