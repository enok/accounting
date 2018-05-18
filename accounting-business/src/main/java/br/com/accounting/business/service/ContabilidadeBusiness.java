package br.com.accounting.business.service;

import br.com.accounting.business.dto.ContabilidadeDTO;
import br.com.accounting.commons.exception.BusinessException;
import br.com.accounting.commons.exception.GenericException;
import br.com.accounting.commons.exception.ValidationException;
import br.com.accounting.commons.business.GenericBusiness;
import br.com.accounting.commons.exception.StoreException;

import java.util.List;

public interface ContabilidadeBusiness extends GenericBusiness<ContabilidadeDTO> {
    void atualizarRecursivamente(ContabilidadeDTO dto) throws StoreException, BusinessException, GenericException;

    List<Long> incrementarRecorrentes(Integer anos) throws StoreException, BusinessException, GenericException;

    void excluirRecursivamente(ContabilidadeDTO dto) throws ValidationException, StoreException, GenericException;

    void realizarPagamento(Long codigo) throws StoreException, BusinessException, GenericException;

    List<ContabilidadeDTO> buscarParcelasRelacionadas(Long codigo) throws StoreException, GenericException;

    List<ContabilidadeDTO> buscarRecorrentesRelacionadas(Long codigo) throws StoreException, GenericException;
}
