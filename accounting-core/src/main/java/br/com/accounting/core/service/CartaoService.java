package br.com.accounting.core.service;

import br.com.accounting.core.entity.Cartao;
import br.com.accounting.core.exception.StoreException;

import java.text.ParseException;

public interface CartaoService extends GenericService<Cartao> {
    Cartao buscarPorNumero(String numero) throws ParseException, StoreException;
}
