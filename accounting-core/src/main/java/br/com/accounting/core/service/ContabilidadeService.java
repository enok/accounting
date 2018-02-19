package br.com.accounting.core.service;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.exception.ServiceException;

import java.util.List;

public interface ContabilidadeService {
    void salvar(Contabilidade contabilidade) throws ServiceException;

    List<Contabilidade> buscarRegistros() throws ServiceException;
}
