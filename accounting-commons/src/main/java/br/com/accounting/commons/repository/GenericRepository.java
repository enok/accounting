package br.com.accounting.commons.repository;

import br.com.accounting.commons.exception.RepositoryException;
import br.com.accounting.commons.exception.StoreException;

import java.text.ParseException;
import java.util.List;

public interface GenericRepository<T> {
    Long proximoCodigo() throws StoreException;

    void incrementarCodigo(Long codigo) throws StoreException, RepositoryException;

    void salvar(T entity) throws StoreException;

    void atualizar(T entity) throws StoreException;

    void deletar(T entity) throws StoreException;

    List<T> buscarRegistros() throws StoreException, ParseException;

    T filtrarCodigo(List<T> entities, Long codigo);
}
