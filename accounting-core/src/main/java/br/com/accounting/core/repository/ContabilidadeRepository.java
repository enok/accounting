package br.com.accounting.core.repository;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.exception.RepositoryException;

import java.util.List;

public interface ContabilidadeRepository {
    Long proximoCodigo();

    void incrementarCodigo(Long proximoCodigo) throws RepositoryException;

    void salvar(Contabilidade contabilidade) throws RepositoryException;

    List<Contabilidade> buscarRegistros() throws RepositoryException;
}
