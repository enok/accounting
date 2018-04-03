package br.com.accounting.core.service;

import br.com.accounting.core.entity.Conta;
import br.com.accounting.core.exception.ServiceException;

public interface ContaService extends GenericService<Conta> {
    void atualizarSaldo(Conta conta, Double saldo) throws ServiceException;

    Conta buscarPorNome(String nome) throws ServiceException;
}
