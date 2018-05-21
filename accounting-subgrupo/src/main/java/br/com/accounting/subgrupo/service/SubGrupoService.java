package br.com.accounting.subgrupo.service;

import br.com.accounting.commons.exception.StoreException;
import br.com.accounting.commons.service.GenericService;
import br.com.accounting.commons.entity.SubGrupo;

import java.text.ParseException;

public interface SubGrupoService extends GenericService<SubGrupo> {
    SubGrupo buscarPorNome(String nome) throws StoreException, ParseException;
}
