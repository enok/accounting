package br.com.accounting.core.service;

import br.com.accounting.core.entity.Conta;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.exception.StoreException;

import java.text.ParseException;
import java.util.List;

public interface ContaService extends GenericService<Conta> {
    void atualizarSaldo(Conta conta, Double saldo) throws StoreException, ServiceException;

    Conta buscarPorNome(String nome) throws StoreException, ParseException;

    List<Conta> buscarCumulativas() throws StoreException, ParseException;
}
