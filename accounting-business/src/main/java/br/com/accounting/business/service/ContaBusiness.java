package br.com.accounting.business.service;

import br.com.accounting.business.dto.ContaDTO;
import br.com.accounting.business.exception.BusinessException;

import java.util.List;

public interface ContaBusiness {
    Long criar(ContaDTO contaDTO) throws BusinessException;

    ContaDTO buscarContaPorId(Long codigo) throws BusinessException;

    List<ContaDTO> buscarContas() throws BusinessException;

    void adicionarCredito(ContaDTO contaDTO, String credito) throws BusinessException;

    void adicionarDebito(ContaDTO contaDTO, String debito) throws BusinessException;

    void excluir(ContaDTO contaDTO) throws BusinessException;

    void transferir(ContaDTO contaOrigemDTO, ContaDTO contaDestinoDTO, String valorDeTransferencia) throws BusinessException;
}
