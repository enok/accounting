package br.com.accounting.business.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static br.com.accounting.core.util.Utils.isBlank;

public class ContabilidadeDTO {
    private String codigo;
    private String dataLancamento;
    private String dataAtualizacao;
    private String vencimento;
    private String tipoPagamento;
    private String subTipoPagamento;
    private String tipo;
    private String grupo;
    private String subGrupo;
    private String descricao;
    private String parcela;
    private String parcelas;
    private String parcelaCodigoPai;
    private String categoria;
    private String valor;
    private String status;

    public String getCodigo() {
        return codigo;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public String getDataAtualizacao() {
        return dataAtualizacao;
    }

    public String getVencimento() {
        return vencimento;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public String getSubTipoPagamento() {
        return subTipoPagamento;
    }

    public String getTipo() {
        return tipo;
    }

    public String getGrupo() {
        return grupo;
    }

    public String getSubGrupo() {
        return subGrupo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getParcela() {
        return parcela;
    }

    public String getParcelas() {
        return parcelas;
    }

    public String getParcelaCodigoPai() {
        return parcelaCodigoPai;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getValor() {
        return valor;
    }

    public String getStatus() {
        return status;
    }

    public ContabilidadeDTO withCodigo(String codigo) {
        if (!isBlank(codigo)) {
            this.codigo = codigo;
        }
        return this;
    }

    public ContabilidadeDTO withDataLancamento(String dataLancamento) {
        if (!isBlank(dataLancamento)) {
            this.dataLancamento = dataLancamento;
        }
        return this;
    }

    public ContabilidadeDTO withDataAtualizacao(String dataAtualizacao) {
        if (!isBlank(dataAtualizacao)) {
            this.dataAtualizacao = dataAtualizacao;
        }
        return this;
    }

    public ContabilidadeDTO withVencimento(String vencimento) {
        if (!isBlank(vencimento)) {
            this.vencimento = vencimento;
        }
        return this;
    }

    public ContabilidadeDTO withTipoPagamento(String tipoPagamento) {
        if (!isBlank(tipoPagamento)) {
            this.tipoPagamento = tipoPagamento;
        }
        return this;
    }

    public ContabilidadeDTO withSubTipoPagamento(String subTipoPagamento) {
        if (!isBlank(subTipoPagamento)) {
            this.subTipoPagamento = subTipoPagamento;
        }
        return this;
    }

    public ContabilidadeDTO withTipo(String tipo) {
        if (!isBlank(tipo)) {
            this.tipo = tipo;
        }
        return this;
    }

    public ContabilidadeDTO withGrupo(String grupo) {
        if (!isBlank(grupo)) {
            this.grupo = grupo;
        }
        return this;
    }

    public ContabilidadeDTO withSubGrupo(String subGrupo) {
        if (!isBlank(subGrupo)) {
            this.subGrupo = subGrupo;
        }
        return this;
    }

    public ContabilidadeDTO withDescricao(String descricao) {
        if (!isBlank(descricao)) {
            this.descricao = descricao;
        }
        return this;
    }

    public ContabilidadeDTO withParcela(String parcela) {
        if (!isBlank(parcela)) {
            this.parcela = parcela;
        }
        return this;
    }

    public ContabilidadeDTO withParcelas(String parcelas) {
        if (!isBlank(parcelas)) {
            this.parcelas = parcelas;
        }
        return this;
    }

    public ContabilidadeDTO withParcelaCodigoPai(String codigoPai) {
        if (!isBlank(codigoPai)) {
            this.parcelaCodigoPai = codigoPai;
        }
        return this;
    }

    public ContabilidadeDTO withCategoria(String categoria) {
        if (!isBlank(categoria)) {
            this.categoria = categoria;
        }
        return this;
    }

    public ContabilidadeDTO withValor(String valor) {
        if (!isBlank(valor)) {
            this.valor = valor;
        }
        return this;
    }

    public ContabilidadeDTO withStatus(String status) {
        if (!isBlank(status)) {
            this.status = status;
        }
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
