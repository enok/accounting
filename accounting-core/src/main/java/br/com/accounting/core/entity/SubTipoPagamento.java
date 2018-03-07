package br.com.accounting.core.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class SubTipoPagamento implements Entity, Serializable {
    private Long codigo;
    private String descricao;

    public SubTipoPagamento() {
    }

    public SubTipoPagamento(Long codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Long getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public SubTipoPagamento withCodigo(Long codigo) {
        this.codigo = codigo;
        return this;
    }

    public SubTipoPagamento withDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        SubTipoPagamento that = (SubTipoPagamento) o;

        return new EqualsBuilder()
                .append(descricao, that.descricao)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(descricao)
                .toHashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
