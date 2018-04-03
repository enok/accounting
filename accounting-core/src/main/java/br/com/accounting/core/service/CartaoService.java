package br.com.accounting.core.service;

import br.com.accounting.core.entity.Cartao;
import br.com.accounting.core.exception.ServiceException;

public interface CartaoService extends GenericService<Cartao> {
    Cartao buscarPorNumero(String numero) throws ServiceException;
}
