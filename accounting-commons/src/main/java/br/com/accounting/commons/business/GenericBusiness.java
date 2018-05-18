package br.com.accounting.commons.business;

import br.com.accounting.commons.exception.BusinessException;
import br.com.accounting.commons.exception.GenericException;
import br.com.accounting.commons.exception.StoreException;
import br.com.accounting.commons.exception.ValidationException;

import java.util.List;

public interface GenericBusiness<D> {
    List<Long> criar(D dto) throws ValidationException, StoreException, GenericException;

    void atualizar(D dto) throws StoreException, BusinessException, GenericException;

    void excluir(D dto) throws BusinessException, StoreException, GenericException;

    D buscarPorCodigo(Long codigo) throws StoreException, BusinessException, GenericException;

    List<D> buscarTodas() throws StoreException, GenericException;
}
