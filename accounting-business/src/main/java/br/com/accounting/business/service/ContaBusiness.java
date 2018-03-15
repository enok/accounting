package br.com.accounting.business.service;

import br.com.accounting.business.dto.ContaDTO;
import br.com.accounting.business.exception.BusinessException;

public interface ContaBusiness {
    Long criar(ContaDTO contaDTO) throws BusinessException;
}
