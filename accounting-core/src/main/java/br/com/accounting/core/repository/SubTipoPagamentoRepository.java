package br.com.accounting.core.repository;

import br.com.accounting.core.entity.SubTipoPagamento;
import br.com.accounting.core.exception.RepositoryException;

import java.util.List;

public interface SubTipoPagamentoRepository {
    Long proximoCodigo();

    void incrementarCodigo(Long proximoCodigo) throws RepositoryException;

    void salvar(SubTipoPagamento subTipoPagamento) throws RepositoryException;

    List<SubTipoPagamento> buscarRegistros() throws RepositoryException;
}
