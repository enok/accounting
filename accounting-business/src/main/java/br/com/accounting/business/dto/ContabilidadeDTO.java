package br.com.accounting.business.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ContabilidadeDTO {
    private String codigo;
    private String dataLancamento;
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
        this.codigo = codigo;
        return this;
    }

    public ContabilidadeDTO withDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
        return this;
    }

    public ContabilidadeDTO withVencimento(String vencimento) {
        this.vencimento = vencimento;
        return this;
    }

    public ContabilidadeDTO withTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
        return this;
    }

    public ContabilidadeDTO withSubTipoPagamento(String subTipoPagamento) {
        this.subTipoPagamento = subTipoPagamento;
        return this;
    }

    public ContabilidadeDTO withTipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public ContabilidadeDTO withGrupo(String grupo) {
        this.grupo = grupo;
        return this;
    }

    public ContabilidadeDTO withSubGrupo(String subGrupo) {
        this.subGrupo = subGrupo;
        return this;
    }

    public ContabilidadeDTO withDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public ContabilidadeDTO withParcela(String parcela) {
        this.parcela = parcela;
        return this;
    }

    public ContabilidadeDTO withParcelas(String parcelas) {
        this.parcelas = parcelas;
        return this;
    }

    public ContabilidadeDTO withParcelaCodigoPai(String codigoPai) {
        this.parcelaCodigoPai = codigoPai;
        return this;
    }

    public ContabilidadeDTO withCategoria(String categoria) {
        this.categoria = categoria;
        return this;
    }

    public ContabilidadeDTO withValor(String valor) {
        this.valor = valor;
        return this;
    }

    public ContabilidadeDTO withStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
