package br.com.accounting.core.repository;

import br.com.accounting.core.entity.Conta;

import java.util.List;

public interface ContaRepository extends GenericRepository<Conta> {
    Conta filtrarCodigo(List<Conta> contas, Long codigo);

    Conta filtrarPorNomeDescricao(List<Conta> contas, String nome, String descricao);
}
