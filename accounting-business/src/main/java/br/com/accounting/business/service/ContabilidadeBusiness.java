package br.com.accounting.business.service;

import br.com.accounting.business.dto.ContabilidadeDTO;
import br.com.accounting.business.exception.BusinessException;

import java.util.List;

public interface ContabilidadeBusiness extends GenericBusiness<ContabilidadeDTO> {
    void atualizarSubsequentes(ContabilidadeDTO dto) throws BusinessException;

    void excluirSubsequentes(ContabilidadeDTO dto) throws BusinessException;

    List<Long> incrementarRecorrentes(Integer anos) throws BusinessException;

    void realizarPagamento(Long codigo) throws BusinessException;

    List<ContabilidadeDTO> buscarTodasAsParcelas(Long codigo) throws BusinessException;

    List<ContabilidadeDTO> buscarTodasAsRecorrentes(Long codigo) throws BusinessException;
}
