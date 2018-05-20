package br.com.accounting.conta.business;

import br.com.accounting.commons.business.GenericBusiness;
import br.com.accounting.commons.exception.BusinessException;
import br.com.accounting.commons.exception.GenericException;
import br.com.accounting.commons.exception.StoreException;
import br.com.accounting.conta.dto.ContaDTO;

public interface ContaBusiness extends GenericBusiness<ContaDTO> {
    void adicionarCredito(ContaDTO contaDTO, Double credito) throws StoreException, GenericException;

    void adicionarDebito(ContaDTO dto, Double debito) throws StoreException, GenericException;

    void transferir(ContaDTO origemDTO, ContaDTO destinoDTO, Double valor) throws StoreException, BusinessException, GenericException;

    void atualizarCumulativas() throws StoreException, BusinessException, GenericException;
}
