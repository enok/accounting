package br.com.accounting.business.service;

import br.com.accounting.business.exception.BusinessException;

import java.util.List;

public interface GenericBusiness<D> {
    List<Long> criar(D dto) throws BusinessException;

    void atualizar(D dto) throws BusinessException;

    void excluir(D dto) throws BusinessException;

    D buscarPorId(Long codigo) throws BusinessException;

    List<D> buscarTodas() throws BusinessException;
}
