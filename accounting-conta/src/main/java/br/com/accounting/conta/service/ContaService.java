package br.com.accounting.conta.service;

import br.com.accounting.commons.exception.ServiceException;
import br.com.accounting.commons.exception.StoreException;
import br.com.accounting.commons.service.GenericService;
import br.com.accounting.commons.entity.Conta;

import java.text.ParseException;
import java.util.List;

public interface ContaService extends GenericService<Conta> {
    void atualizarSaldo(Conta conta, Double saldo) throws StoreException, ServiceException;

    Conta buscarPorNome(String nome) throws StoreException, ParseException;

    List<Conta> buscarCumulativas() throws StoreException, ParseException;
}
