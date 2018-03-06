package br.com.accounting.business.service;

import br.com.accounting.business.dto.ContabilidadeDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.TechnicalException;

import java.util.List;

public interface ContabilidadeBusiness {
    List<Long> salvar(ContabilidadeDTO contabilidadeDTO) throws BusinessException, TechnicalException;

    void atualizar(ContabilidadeDTO contabilidadeDTO) throws TechnicalException;

    List<ContabilidadeDTO> buscarTudo() throws TechnicalException;

    List<ContabilidadeDTO> buscarRelacionadasPorCodigoPai(Long codigo) throws TechnicalException;
}
