package br.com.accounting.core.service;

import br.com.accounting.core.entity.Conta;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.exception.StoreException;

import java.util.List;

public interface ContaService extends GenericService<Conta> {
    void atualizarSaldo(Conta conta, Double saldo) throws ServiceException;

    Conta buscarPorNome(String nome) throws ServiceException, StoreException;

    List<Conta> buscarCumulativas() throws ServiceException, StoreException;
}
