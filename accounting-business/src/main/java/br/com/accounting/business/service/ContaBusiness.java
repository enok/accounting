package br.com.accounting.business.service;

import br.com.accounting.business.dto.ContaDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.core.entity.Conta;

import java.util.List;

public interface ContaBusiness extends GenericBusiness<ContaDTO, Conta> {
    Long criar(ContaDTO contaDTO) throws BusinessException;

    void atualizar(ContaDTO contaDTO) throws BusinessException;

    void adicionarCredito(ContaDTO contaDTO, String credito) throws BusinessException;

    void adicionarDebito(ContaDTO contaDTO, String debito) throws BusinessException;

    void transferir(ContaDTO contaOrigemDTO, ContaDTO contaDestinoDTO, String valorDeTransferencia) throws BusinessException;

    void excluir(ContaDTO contaDTO) throws BusinessException;

    ContaDTO buscarPorId(Long codigo) throws BusinessException;

    List<ContaDTO> buscarTodas() throws BusinessException;
}
