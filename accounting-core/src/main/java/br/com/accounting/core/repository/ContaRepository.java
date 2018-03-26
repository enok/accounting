package br.com.accounting.core.repository;

import br.com.accounting.core.entity.Conta;

import java.util.List;

public interface ContaRepository extends GenericRepository<Conta> {
    Conta filtrarPorNome(List<Conta> contas, String nome);

    void ordenarPorNomeDescricao(List<Conta> contas);
}
