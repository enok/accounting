package br.com.accounting.business.service;

import br.com.accounting.business.dto.ContaDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.core.exception.StoreException;

public interface ContaBusiness extends GenericBusiness<ContaDTO> {
    void adicionarCredito(ContaDTO contaDTO, String credito) throws BusinessException;

    void adicionarDebito(ContaDTO contaDTO, String debito) throws BusinessException;

    void transferir(ContaDTO contaOrigemDTO, ContaDTO contaDestinoDTO, String valorDeTransferencia) throws BusinessException;

    void atualizarContas() throws StoreException, BusinessException;
}
