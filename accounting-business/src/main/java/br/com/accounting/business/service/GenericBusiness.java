package br.com.accounting.business.service;

import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.GenericException;
import br.com.accounting.business.exception.ValidationException;
import br.com.accounting.core.exception.StoreException;

import java.util.List;

public interface GenericBusiness<D> {
    List<Long> criar(D dto) throws ValidationException, StoreException, GenericException;

    void atualizar(D dto) throws ValidationException, StoreException, GenericException;

    void excluir(D dto) throws BusinessException, StoreException, GenericException;

    D buscarPorCodigo(Long codigo) throws StoreException, BusinessException, GenericException;

    List<D> buscarTodas() throws StoreException, GenericException;
}
