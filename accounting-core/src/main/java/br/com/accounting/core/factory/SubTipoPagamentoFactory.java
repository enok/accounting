package br.com.accounting.core.factory;

import br.com.accounting.core.entity.SubTipoPagamento;

import static br.com.accounting.core.util.Utils.isEmpty;

public class SubTipoPagamentoFactory {
    private static SubTipoPagamentoFactory subTipoPagamentoFactory;
    private SubTipoPagamento subTipoPagamento;

    private SubTipoPagamentoFactory() {
        subTipoPagamento = new SubTipoPagamento();
    }

    public static SubTipoPagamentoFactory begin() {
        subTipoPagamentoFactory = new SubTipoPagamentoFactory();
        return subTipoPagamentoFactory;
    }

    public SubTipoPagamentoFactory withCodigo(String codigo) {
        if (!isEmpty(codigo)) {
            subTipoPagamento.withCodigo(Long.parseLong(codigo));
        }
        return this;
    }

    public SubTipoPagamentoFactory withDescricao(String descricao) {
        if (!isEmpty(descricao)) {
            subTipoPagamento.withDescricao(descricao);
        }
        return this;
    }

    public SubTipoPagamento build() {
        return subTipoPagamento;
    }
}
