package br.com.accounting.business.service;

import br.com.accounting.business.dto.ContaDTO;
import br.com.accounting.commons.exception.BusinessException;
import br.com.accounting.commons.exception.GenericException;
import br.com.accounting.commons.business.GenericBusiness;
import br.com.accounting.commons.exception.StoreException;

public interface ContaBusiness extends GenericBusiness<ContaDTO> {
    void adicionarCredito(ContaDTO contaDTO, Double credito) throws StoreException, GenericException;

    void adicionarDebito(ContaDTO dto, Double debito) throws StoreException, GenericException;

    void transferir(ContaDTO origemDTO, ContaDTO destinoDTO, Double valor) throws StoreException, BusinessException, GenericException;

    void atualizarCumulativas() throws StoreException, BusinessException, GenericException;
}
