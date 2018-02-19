package br.com.accounting.core.factory;

import br.com.accounting.core.entity.SubTipoPagamento;

public class SubTipoPagamentoFactoryMock {
    public static SubTipoPagamento create() {
        return SubTipoPagamentoFactory
                .begin()
                .withDescricao("744")
                .build();
    }
}
