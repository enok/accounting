package br.com.accounting.core.repository;

import br.com.accounting.core.entity.SubGrupo;
import br.com.accounting.core.exception.RepositoryException;

import java.util.List;

public interface SubGrupoRepository {
    Long proximoCodigo();

    void incrementarCodigo(Long proximoCodigo) throws RepositoryException;

    void salvar(SubGrupo subGrupo) throws RepositoryException;

    List<SubGrupo> buscarRegistros() throws RepositoryException;
}
