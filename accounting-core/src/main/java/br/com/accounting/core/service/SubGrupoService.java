package br.com.accounting.core.service;

import br.com.accounting.commons.exception.StoreException;
import br.com.accounting.commons.service.GenericService;
import br.com.accounting.core.entity.SubGrupo;

import java.text.ParseException;

public interface SubGrupoService extends GenericService<SubGrupo> {
    SubGrupo buscarPorNome(String nome) throws StoreException, ParseException;
}
