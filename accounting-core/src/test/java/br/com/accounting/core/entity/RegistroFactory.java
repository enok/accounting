package br.com.accounting.core.entity;

import java.time.LocalDate;

import static br.com.accounting.core.entity.Categoria.ENTRADA;
import static br.com.accounting.core.entity.Categoria.SAIDA;
import static br.com.accounting.core.entity.Status.NAO_PAGO;
import static br.com.accounting.core.entity.Status.PAGO;
import static br.com.accounting.core.entity.Tipo.FIXO;
import static br.com.accounting.core.entity.Tipo.VARIAVEL;
import static br.com.accounting.core.entity.TipoPagamento.CARTAO_CREDITO;
import static br.com.accounting.core.entity.TipoPagamento.CARTAO_DEBITO;
import static br.com.accounting.core.entity.TipoPagamento.DINHEIRO;

public class RegistroFactory {

    public static Registro createCartaoCredito() {
        LocalDate vencimento = LocalDate.of(2018, 1, 27);
        Parcelamento parcelamento = new Parcelamento(1, 12);
        SubTipoPagamento subTipoPagamento =  new SubTipoPagamento(1L, "744");

        Registro registro = new Registro()
                .withVencimento(vencimento)
                .withTipoPagamento(CARTAO_CREDITO)
                .withSubTipoPagamento(subTipoPagamento)
                .withTipo(FIXO)
                .withGrupo("MORADIA")
                .withSubGrupo("ASSINATURAS")
                .withDescricao("spotify")
                .withParcelamento(parcelamento)
                .withCategoria(SAIDA)
                .withValor(26.90)
                .withStatus(PAGO);

        return registro;
    }

    public static Registro createDinheiro() {
        LocalDate vencimento = LocalDate.of(2018, 1, 25);

        Registro registro = new Registro()
                .withVencimento(vencimento)
                .withTipoPagamento(DINHEIRO)
                .withTipo(FIXO)
                .withGrupo("RENDIMENTOS")
                .withSubGrupo("SALARIO")
                .withDescricao("sysmap")
                .withCategoria(ENTRADA)
                .withValor(5000.0)
                .withStatus(NAO_PAGO);

        return registro;
    }

    public static Registro createCartaoDebito() {
        LocalDate vencimento = LocalDate.of(2018, 1, 15);
        SubTipoPagamento subTipoPagamento =  new SubTipoPagamento(1L, "7660");

        Registro registro = new Registro()
                .withVencimento(vencimento)
                .withTipoPagamento(CARTAO_DEBITO)
                .withSubTipoPagamento(subTipoPagamento)
                .withTipo(VARIAVEL)
                .withGrupo("MERCADO")
                .withSubGrupo("PADARIA")
                .withDescricao("pão")
                .withCategoria(SAIDA)
                .withValor(18.0)
                .withStatus(PAGO);

        return registro;
    }
}
