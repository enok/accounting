package br.com.accounting.core.entity;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Parcelamento {
    private Integer parcela;
    private Integer parcelas;

    public Parcelamento() {
    }

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

    public Parcelamento withParcela(Integer parcela) {
        this.parcela = parcela;
        return this;
    }

    public Parcelamento withParcelas(Integer parcelas) {
        this.parcelas = parcelas;
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
