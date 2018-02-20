package br.com.accounting.core.factory;

import br.com.accounting.core.entity.*;

import java.time.LocalDate;

import static br.com.accounting.core.util.Utils.getDateFromString;
import static br.com.accounting.core.util.Utils.isEmpty;

public final class ContabilidadeFactory {
    private static ContabilidadeFactory contabilidadeFactory;
    private Contabilidade contabilidade;

    private ContabilidadeFactory() {
        contabilidade = new Contabilidade();
    }

    public static ContabilidadeFactory begin() {
        contabilidadeFactory = new ContabilidadeFactory();
        return contabilidadeFactory;
    }

    public ContabilidadeFactory withCodigo(String codigo) {
        if (!isEmpty(codigo)) {
            contabilidade.withCodigo(Long.parseLong(codigo));
        }
        return this;
    }

    public ContabilidadeFactory withDataLancamento(String dataLancamento) {
        if (!isEmpty(dataLancamento)) {
            LocalDate dataLancamentoObject = getDateFromString(dataLancamento);
            contabilidade.withDataLancamento(dataLancamentoObject);
        }
        return this;
    }

    public ContabilidadeFactory withVencimento(String vencimento) {
        if (!isEmpty(vencimento)) {
            LocalDate vencimentoObj = getDateFromString(vencimento);
            contabilidade.withVencimento(vencimentoObj);
        }
        return this;
    }

    public ContabilidadeFactory withTipoPagamento(String tipoPagamento) {
        if (!isEmpty(tipoPagamento)) {
            TipoPagamento tipoPagamentoObj = TipoPagamento.valueOf(tipoPagamento);
            contabilidade.withTipoPagamento(tipoPagamentoObj);
        }
        return this;
    }

    public ContabilidadeFactory withSubTipoPagamento(String codigo, String descricao) {
        if (!isEmpty(codigo) && !isEmpty(descricao)) {
            SubTipoPagamento subTipoPagamentoObj = new SubTipoPagamento(Long.parseLong(codigo), descricao);
            contabilidade.withSubTipoPagamento(subTipoPagamentoObj);
        }
        return this;
    }

    public ContabilidadeFactory withSubTipoPagamento(String descricao) {
        if (!isEmpty(descricao)) {
            SubTipoPagamento subTipoPagamentoObj = new SubTipoPagamento()
                    .withDescricao(descricao);
            contabilidade.withSubTipoPagamento(subTipoPagamentoObj);
        }
        return this;
    }

    public ContabilidadeFactory withTipo(String tipo) {
        if (!isEmpty(tipo)) {
            Tipo tipoObj = Tipo.valueOf(tipo);
            contabilidade.withTipo(tipoObj);
        }
        return this;
    }

    public ContabilidadeFactory withGrupo(String grupo) {
        if (!isEmpty(grupo)) {
            contabilidade.withGrupo(grupo);
        }
        return this;
    }

    public ContabilidadeFactory withSubGrupo(String subGrupo) {
        if (!isEmpty(subGrupo)) {
            contabilidade.withSubGrupo(subGrupo);
        }
        return this;
    }

    public ContabilidadeFactory withDescricao(String descricao) {
        if (!isEmpty(descricao)) {
            contabilidade.withDescricao(descricao);
        }
        return this;
    }

    public ContabilidadeFactory withParcelamento(String parcela, String parcelas) {
        if (!isEmpty(parcela) && !isEmpty(parcelas)) {
            Parcelamento parcelamento = new Parcelamento(Integer.parseInt(parcela), Integer.parseInt(parcelas));
            contabilidade.withParcelamento(parcelamento);
        }
        return this;
    }

    public ContabilidadeFactory withCategoria(String categoria) {
        if (!isEmpty(categoria)) {
            Categoria categoriaObj = Categoria.valueOf(categoria);
            contabilidade.withCategoria(categoriaObj);
        }
        return this;
    }

    public ContabilidadeFactory withValor(String valor) {
        if (!isEmpty(valor)) {
            contabilidade.withValor(Double.parseDouble(valor));
        }
        return this;
    }

    public ContabilidadeFactory withStatus(String status) {
        if (!isEmpty(status)) {
            Status statusObj = Status.valueOf(status);
            contabilidade.withStatus(statusObj);
        }
        return this;
    }

    public Contabilidade build() {
        return contabilidade;
    }
}
