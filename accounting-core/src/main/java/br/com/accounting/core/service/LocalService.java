package br.com.accounting.core.service;

import br.com.accounting.commons.exception.StoreException;
import br.com.accounting.commons.service.GenericService;
import br.com.accounting.core.entity.Local;

import java.text.ParseException;

public interface LocalService extends GenericService<Local> {
    Local buscarPorNome(String nome) throws StoreException, ParseException;
}
