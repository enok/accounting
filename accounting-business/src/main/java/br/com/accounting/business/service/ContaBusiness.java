package br.com.accounting.business.service;

import br.com.accounting.business.dto.ContaDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.GenericException;
import br.com.accounting.core.exception.StoreException;

public interface ContaBusiness extends GenericBusiness<ContaDTO> {
    void adicionarCredito(ContaDTO contaDTO, Double credito) throws StoreException, BusinessException, GenericException;

    void adicionarDebito(ContaDTO dto, Double debito) throws StoreException, BusinessException, GenericException;

    void transferir(ContaDTO origemDTO, ContaDTO destinoDTO, Double valor) throws BusinessException;

    void atualizarContas() throws StoreException, BusinessException;
}
