package br.com.accounting.local.repository;

import br.com.accounting.commons.repository.GenericRepository;
import br.com.accounting.commons.entity.Local;

import java.util.List;

public interface LocalRepository extends GenericRepository<Local> {
    Local filtrarPorNome(List<Local> contas, String nome);

    void ordenarPorNome(List<Local> contas);
}
