package br.com.accounting.core.service;

import br.com.accounting.core.entity.SubGrupo;
import br.com.accounting.core.exception.StoreException;

import java.text.ParseException;

public interface SubGrupoService extends GenericService<SubGrupo> {
    SubGrupo buscarPorNome(String nome) throws StoreException, ParseException;
}
