package br.com.accounting.core.entity;

public class SubTipoPagamentoFactory {
    public static SubTipoPagamento create() {
        SubTipoPagamento subTipoPagamento = new SubTipoPagamento()
                .withDescricao("744");
        return subTipoPagamento;
    }
}
