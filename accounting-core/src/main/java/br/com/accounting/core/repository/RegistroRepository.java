package br.com.accounting.core.repository;

import br.com.accounting.core.entity.Registro;
import br.com.accounting.core.exception.RepositoryException;

public interface RegistroRepository {
    void salvar(Registro registro) throws RepositoryException;
}
