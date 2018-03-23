package br.com.accounting.business.service;

import br.com.accounting.business.dto.CartaoDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.core.entity.Cartao;

public interface CartaoBusiness extends GenericBusiness<CartaoDTO, Cartao> {
    Long criar(CartaoDTO cartaoDTO) throws BusinessException;

    void atualizar(CartaoDTO cartaoDTO) throws BusinessException;

    CartaoDTO buscarCartaoPorId(Long codigo) throws BusinessException;
}
