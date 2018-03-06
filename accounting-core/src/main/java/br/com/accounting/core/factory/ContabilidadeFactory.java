package br.com.accounting.core.factory;

import br.com.accounting.core.entity.*;

import java.text.ParseException;
import java.time.LocalDate;

import static br.com.accounting.core.util.Utils.*;

public final class ContabilidadeFactory {
    private static ContabilidadeFactory contabilidadeFactory;
    private Contabilidade contabilidade;

    private ContabilidadeFactory() {
        contabilidade = new Contabilidade();
    }

    private ContabilidadeFactory(Contabilidade contabilidade) {
        this.contabilidade = contabilidade;
    }

    public static ContabilidadeFactory begin() {
        contabilidadeFactory = new ContabilidadeFactory();
        return contabilidadeFactory;
    }

    public static ContabilidadeFactory begin(Contabilidade contabilidade) {
        contabilidadeFactory = new ContabilidadeFactory(contabilidade);
        return contabilidadeFactory;
    }

    public ContabilidadeFactory withCodigo(String codigo) {
        if (!isBlank(codigo)) {
            contabilidade.withCodigo(Long.parseLong(codigo));
        }
        return this;
    }

    public ContabilidadeFactory withDataLancamento(String dataLancamento) {
        if (!isBlank(dataLancamento)) {
            LocalDate dataLancamentoObject = getDateFromString(dataLancamento);
            contabilidade.withDataLancamento(dataLancamentoObject);
        }
        return this;
    }

    public ContabilidadeFactory withDataLancamento(LocalDate localDate) {
        if (localDate != null) {
            contabilidade.withDataLancamento(localDate);
        }
        return this;
    }

    public ContabilidadeFactory withDataAtualizacao(String dataAtualizacao) {
        if (!isBlank(dataAtualizacao)) {
            LocalDate dataAtualizacaoObj = getDateFromString(dataAtualizacao);
            contabilidade.withDataAtualizacao(dataAtualizacaoObj);
        }
        return this;
    }

    public ContabilidadeFactory withDataAtualizacao(LocalDate localDate) {
        if (localDate != null) {
            contabilidade.withDataAtualizacao(localDate);
        }
        return this;
    }

    public ContabilidadeFactory withVencimento(String vencimento) {
        if (!isBlank(vencimento)) {
            LocalDate vencimentoObj = getDateFromString(vencimento);
            contabilidade.withVencimento(vencimentoObj);
        }
        return this;
    }

    public ContabilidadeFactory withTipoPagamento(String tipoPagamento) {
        if (!isBlank(tipoPagamento)) {
            TipoPagamento tipoPagamentoObj = TipoPagamento.valueOf(tipoPagamento);
            contabilidade.withTipoPagamento(tipoPagamentoObj);
        }
        return this;
    }

    public ContabilidadeFactory withSubTipoPagamento(String codigo, String descricao) {
        if (!isBlank(codigo) && !isBlank(descricao)) {
            SubTipoPagamento subTipoPagamentoObj = new SubTipoPagamento(Long.parseLong(codigo), descricao);
            contabilidade.withSubTipoPagamento(subTipoPagamentoObj);
        }
        return this;
    }

    public ContabilidadeFactory withSubTipoPagamento(String descricao) {
        if (!isBlank(descricao)) {
            SubTipoPagamento subTipoPagamentoObj = new SubTipoPagamento()
                    .withDescricao(descricao);
            contabilidade.withSubTipoPagamento(subTipoPagamentoObj);
        }
        return this;
    }

    public ContabilidadeFactory withTipo(String tipo) {
        if (!isBlank(tipo)) {
            Tipo tipoObj = Tipo.valueOf(tipo);
            contabilidade.withTipo(tipoObj);
        }
        return this;
    }

    public ContabilidadeFactory withGrupoSubGrupo(String grupo, String subGrupo) {
        if (!isBlank(grupo)) {
            SubGrupo subGrupoObj = SubGrupoFactory
                    .begin()
                    .withDescricao(subGrupo)
                    .build();
            Grupo grupoObj = GrupoFactory
                    .begin()
                    .withDescricao(grupo)
                    .withSubGrupo(subGrupoObj).build();
            contabilidade.withGrupo(grupoObj);
        }
        return this;
    }

    public ContabilidadeFactory withDescricao(String descricao) {
        if (!isBlank(descricao)) {
            contabilidade.withDescricao(descricao);
        }
        return this;
    }

    public ContabilidadeFactory withParcelamento(String parcela, String parcelas, String codigoParcelamentoPai) {
        if (!isBlank(parcela) && !isBlank(parcelas)) {

            Parcelamento parcelamento;

            if (!isBlank(codigoParcelamentoPai)) {
                parcelamento = new Parcelamento(Integer.parseInt(parcela), Integer.parseInt(parcelas), Long.parseLong(codigoParcelamentoPai));
            }
            else {
                parcelamento = new Parcelamento(Integer.parseInt(parcela), Integer.parseInt(parcelas));
            }

            contabilidade.withParcelamento(parcelamento);
        }
        return this;
    }

    public ContabilidadeFactory withParcelamento(Integer parcela, Integer parcelas, Long codigoPai) {
        Parcelamento parcelamento = new Parcelamento(parcela, parcelas, codigoPai);
        contabilidade.withParcelamento(parcelamento);
        return this;
    }

    public ContabilidadeFactory withParcelamento(String parcela, String parcelas) {
        return withParcelamento(parcela, parcelas, null);
    }

    public ContabilidadeFactory withCategoria(String categoria) {
        if (!isBlank(categoria)) {
            Categoria categoriaObj = Categoria.valueOf(categoria);
            contabilidade.withCategoria(categoriaObj);
        }
        return this;
    }

    public ContabilidadeFactory withValor(String valor) throws ParseException {
        if (!isBlank(valor)) {
            contabilidade.withValor(createDouble(valor));
        }
        return this;
    }

    public ContabilidadeFactory withStatus(String status) {
        if (!isBlank(status)) {
            Status statusObj = Status.valueOf(status);
            contabilidade.withStatus(statusObj);
        }
        return this;
    }

    public Contabilidade build() {
        return contabilidade;
    }
}
