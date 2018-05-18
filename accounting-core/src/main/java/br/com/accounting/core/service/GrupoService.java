package br.com.accounting.core.service;

import br.com.accounting.commons.exception.StoreException;
import br.com.accounting.commons.service.GenericService;
import br.com.accounting.core.entity.Grupo;

import java.text.ParseException;

public interface GrupoService extends GenericService<Grupo> {
    Grupo buscarPorNome(String nome) throws StoreException, ParseException;
}
