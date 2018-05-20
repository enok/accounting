package br.com.accounting.conta.repository;

import br.com.accounting.commons.entity.Conta;
import br.com.accounting.commons.repository.GenericRepository;

import java.util.List;

public interface ContaRepository extends GenericRepository<Conta> {
    Conta filtrarPorNome(List<Conta> contas, String nome);

    List<Conta> filtrarCumulativas(List<Conta> contas);

    void ordenarPorNome(List<Conta> contas);
}
