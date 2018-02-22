package br.com.accounting.core.factory;

import br.com.accounting.core.entity.SubTipoPagamento;

public class SubTipoPagamentoFactoryMock {
    public static SubTipoPagamento create744() {
        return SubTipoPagamentoFactory
                .begin()
                .withDescricao("744")
                .build();
    }

    public static SubTipoPagamento create7660() {
        return SubTipoPagamentoFactory
                .begin()
                .withDescricao("7660")
                .build();
    }

    public static SubTipoPagamento create744_2() {
        return SubTipoPagamentoFactory
                .begin()
                .withDescricao("744")
                .build();
    }
}
