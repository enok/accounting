package br.com.accounting.business.service;

import br.com.accounting.business.dto.ContabilidadeDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.GenericException;
import br.com.accounting.core.exception.StoreException;

import java.util.List;

public interface ContabilidadeBusiness extends GenericBusiness<ContabilidadeDTO> {
    void atualizarRecursivamente(ContabilidadeDTO dto) throws StoreException, BusinessException, GenericException;

    void excluirRecursivamente(ContabilidadeDTO dto) throws StoreException, BusinessException, GenericException;

    List<Long> incrementarRecorrentes(Integer anos) throws StoreException, BusinessException, GenericException;

    void realizarPagamento(Long codigo) throws StoreException, BusinessException;

    List<ContabilidadeDTO> buscarTodasAsParcelas(Long codigo) throws BusinessException;

    List<ContabilidadeDTO> buscarTodasAsRecorrentes(Long codigo) throws BusinessException;
}
