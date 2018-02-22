package br.com.accounting.core.service;

import br.com.accounting.core.entity.SubTipoPagamento;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.factory.SubTipoPagamentoFactoryMock;

import java.util.List;

public abstract class SubTipoPagamentoGenericTest extends GenericTest {
    private SubTipoPagamentoService subTipoPagamentoService;

    public void setSubTipoPagamentoService(SubTipoPagamentoService subTipoPagamentoService) {
        this.subTipoPagamentoService = subTipoPagamentoService;
    }

    protected List<SubTipoPagamento> getSubTipoPagamentos() {
        List<SubTipoPagamento> registros = null;
        try {
            registros = subTipoPagamentoService.buscarRegistros();
        } catch (ServiceException e) {
        }
        return registros;
    }

    protected void criarVariasSubTipoPagamentos() throws ServiceException {
        SubTipoPagamento subTipoPagamento = SubTipoPagamentoFactoryMock.create7660();
        subTipoPagamentoService.salvar(subTipoPagamento);

        subTipoPagamento = SubTipoPagamentoFactoryMock.create744_2();
        subTipoPagamentoService.salvar(subTipoPagamento);

        subTipoPagamento = SubTipoPagamentoFactoryMock.create744();
        subTipoPagamentoService.salvar(subTipoPagamento);
    }
}
