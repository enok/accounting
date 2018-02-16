package br.com.accounting.core.service;

import br.com.accounting.core.entity.SubTipoPagamento;
import br.com.accounting.core.exception.ServiceException;

import java.util.List;

public interface SubTipoPagamentoService {
    void salvar(SubTipoPagamento subTipoPagamento) throws ServiceException;

    List<SubTipoPagamento> buscarRegistros() throws ServiceException;
}
