package br.com.accounting.core.service;

import br.com.accounting.core.entity.Order;
import br.com.accounting.core.entity.SubTipoPagamento;
import br.com.accounting.core.exception.ServiceException;

import java.util.List;

public interface SubTipoPagamentoService extends GenericService<SubTipoPagamento> {
    List<SubTipoPagamento> filtrarPorDescricao(String descricao, List<SubTipoPagamento> subTipoPagamentos) throws ServiceException;

    List<SubTipoPagamento> filtrarPorDescricao(String descricao) throws ServiceException;

    List<SubTipoPagamento> ordenarPorDescricao(Order order, List<SubTipoPagamento> subTipoPagamentos) throws ServiceException;

    List<SubTipoPagamento> ordenarPorDescricao(Order order) throws ServiceException;
}
