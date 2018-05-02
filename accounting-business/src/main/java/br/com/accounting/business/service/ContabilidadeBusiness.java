package br.com.accounting.business.service;

import br.com.accounting.business.dto.ContabilidadeDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.core.exception.StoreException;

import java.util.List;

public interface ContabilidadeBusiness extends GenericBusiness<ContabilidadeDTO> {
    void atualizarSubsequentes(ContabilidadeDTO dto) throws StoreException, BusinessException;

    void excluirSubsequentes(ContabilidadeDTO dto) throws StoreException, BusinessException;

    List<Long> incrementarRecorrentes(Integer anos) throws StoreException, BusinessException;

    void realizarPagamento(Long codigo) throws StoreException, BusinessException;

    List<ContabilidadeDTO> buscarTodasAsParcelas(Long codigo) throws BusinessException;

    List<ContabilidadeDTO> buscarTodasAsRecorrentes(Long codigo) throws BusinessException;
}
