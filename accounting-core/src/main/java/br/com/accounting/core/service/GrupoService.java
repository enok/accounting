package br.com.accounting.core.service;

import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.exception.StoreException;

import java.text.ParseException;

public interface GrupoService extends GenericService<Grupo> {
    Grupo buscarPorNome(String nome) throws StoreException, ParseException;
}
