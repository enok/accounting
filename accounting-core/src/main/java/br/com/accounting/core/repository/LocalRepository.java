package br.com.accounting.core.repository;

import br.com.accounting.core.entity.Local;

import java.util.List;

public interface LocalRepository extends GenericRepository<Local> {
    Local filtrarPorNome(List<Local> contas, String nome);

    void ordenarPorNome(List<Local> contas);
}
