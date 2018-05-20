package br.com.accounting.local.service;

import br.com.accounting.commons.exception.StoreException;
import br.com.accounting.commons.service.GenericService;
import br.com.accounting.commons.entity.Local;

import java.text.ParseException;

public interface LocalService extends GenericService<Local> {
    Local buscarPorNome(String nome) throws StoreException, ParseException;
}
