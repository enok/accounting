package br.com.accounting.core.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Parcelamento {
    private Integer parcela;
    private Integer parcelas;

    public Parcelamento(Integer parcela, Integer parcelas) {
        this.parcela = parcela;
        this.parcelas = parcelas;
    }

    public Integer getParcela() {
        return parcela;
    }

    public Integer getParcelas() {
        return parcelas;
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
