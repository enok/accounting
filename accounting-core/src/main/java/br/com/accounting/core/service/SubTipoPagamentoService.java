package br.com.accounting.core.service;

import br.com.accounting.core.entity.SubTipoPagamento;
import br.com.accounting.core.exception.ServiceException;

public interface SubTipoPagamentoService {
    void salvar(SubTipoPagamento subTipoPagamento) throws ServiceException;
}
