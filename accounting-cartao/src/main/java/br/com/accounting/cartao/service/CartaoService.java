package br.com.accounting.cartao.service;

import br.com.accounting.commons.entity.Cartao;
import br.com.accounting.commons.exception.StoreException;
import br.com.accounting.commons.service.GenericService;

import java.text.ParseException;

public interface CartaoService extends GenericService<Cartao> {
    Cartao buscarPorNumero(String numero) throws ParseException, StoreException;
}
