package br.com.accounting.grupo.service;

import br.com.accounting.commons.entity.Grupo;
import br.com.accounting.commons.exception.StoreException;
import br.com.accounting.commons.service.GenericService;

import java.text.ParseException;

public interface GrupoService extends GenericService<Grupo> {
    Grupo buscarPorNome(String nome) throws StoreException, ParseException;
}
