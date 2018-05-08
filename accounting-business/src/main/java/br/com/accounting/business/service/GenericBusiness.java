package br.com.accounting.business.service;

import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.GenericException;
import br.com.accounting.core.exception.StoreException;

import java.util.List;

public interface GenericBusiness<D> {
    List<Long> criar(D dto) throws StoreException, BusinessException, GenericException;

    void atualizar(D dto) throws StoreException, BusinessException, GenericException;

    void excluir(D dto) throws StoreException, BusinessException;

    D buscarPorId(Long codigo) throws StoreException, BusinessException;

    List<D> buscarTodas() throws StoreException, BusinessException;
}
