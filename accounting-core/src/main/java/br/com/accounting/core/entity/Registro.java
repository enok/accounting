package br.com.accounting.core.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Registro implements Serializable {
    private Long codigo;
    private LocalDate vencimento;
    private TipoPagamento tipoPagamento;
    private String subTipoPagamento;
    private Tipo tipo;
    private String grupo;
    private String subGrupo;
    private String descricao;
    private Parcelamento parcelamento;
    private Categoria categoria;
    private Double valor;
    private Status status;

    public Long getCodigo() {
        return codigo;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public String getVencimentoFormatado() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return getVencimento().format(formatador);
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public String getSubTipoPagamento() {
        return subTipoPagamento;
    }

    public Tipo getTipo() {
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

    public Parcelamento getParcelamento() {
        return parcelamento;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Double getValor() {
        return valor;
    }

    public Status getStatus() {
        return status;
    }

    public Registro withCodigo(Long codigo) {
        this.codigo = codigo;
        return this;
    }

    public Registro withVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
        return this;
    }

    public Registro withTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
        return this;
    }

    public Registro withSubTipoPagamento(String subTipoPagamento) {
        this.subTipoPagamento = subTipoPagamento;
        return this;
    }

    public Registro withTipo(Tipo tipo) {
        this.tipo = tipo;
        return this;
    }

    public Registro withGrupo(String grupo) {
        this.grupo = grupo;
        return this;
    }

    public Registro withSubGrupo(String subGrupo) {
        this.subGrupo = subGrupo;
        return this;
    }

    public Registro withDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public Registro withParcelamento(Parcelamento parcelamento) {
        this.parcelamento = parcelamento;
        return this;
    }

    public Registro withCategoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public Registro withValor(Double valor) {
        this.valor = valor;
        return this;
    }

    public Registro withStatus(Status status) {
        this.status = status;
        return this;
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
