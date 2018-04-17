package br.com.accounting.business.service;

import br.com.accounting.business.dto.ContabilidadeDTO;
import br.com.accounting.business.exception.BusinessException;

import java.util.List;

public interface ContabilidadeBusiness extends GenericBusiness<ContabilidadeDTO> {
    List<Long> criarRecorrente(ContabilidadeDTO dto, Integer meses) throws BusinessException;

    List<Long> atualizarRecorrentes(Integer anos) throws BusinessException;

    void atualizarRecursivamente(ContabilidadeDTO dto) throws BusinessException;

    void realizarPagamento(Long codigo) throws BusinessException;

    List<ContabilidadeDTO> buscarTodasAsParcelas(Long codigo) throws BusinessException;
}
