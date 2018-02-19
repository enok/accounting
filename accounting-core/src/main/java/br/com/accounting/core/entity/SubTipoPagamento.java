package br.com.accounting.core.entity;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SubTipoPagamento implements Entity {
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
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}