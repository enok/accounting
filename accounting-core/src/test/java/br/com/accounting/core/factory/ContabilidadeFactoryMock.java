package br.com.accounting.core.factory;

import br.com.accounting.core.entity.Contabilidade;

public class ContabilidadeFactoryMock {

    public static Contabilidade createCartaoCredito() {
        return ContabilidadeFactory
                .begin()
                .withDataLancamento("01/01/2018")
                .withVencimento("27/01/2018")
                .withTipoPagamento("CARTAO_CREDITO")
                .withSubTipoPagamento("1", "744")
                .withTipo("FIXO")
                .withGrupo("MORADIA")
                .withSubGrupo("ASSINATURAS")
                .withDescricao("spotify")
                .withParcelamento("1", "12")
                .withCategoria("SAIDA")
                .withValor("26.90")
                .withStatus("PAGO")
                .build();
    }

    public static Contabilidade createDinheiro() {
        return ContabilidadeFactory
                .begin()
                .withDataLancamento("01/01/2018")
                .withVencimento("25/01/2018")
                .withTipoPagamento("DINHEIRO")
                .withTipo("FIXO")
                .withGrupo("RENDIMENTOS")
                .withSubGrupo("SALARIO")
                .withDescricao("sysmap")
                .withCategoria("ENTRADA")
                .withValor("5000.0")
                .withStatus("NAO_PAGO")
                .build();
    }

    public static Contabilidade createCartaoDebito() {
        return ContabilidadeFactory
                .begin()
                .withDataLancamento("01/01/2018")
                .withVencimento("15/01/2018")
                .withTipoPagamento("CARTAO_DEBITO")
                .withSubTipoPagamento("1", "7660")
                .withTipo("VARIAVEL")
                .withGrupo("MERCADO")
                .withSubGrupo("PADARIA")
                .withDescricao("pão")
                .withCategoria("SAIDA")
                .withValor("18.0")
                .withStatus("PAGO")
                .build();
    }
}