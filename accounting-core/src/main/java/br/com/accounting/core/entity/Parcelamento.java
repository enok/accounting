package br.com.accounting.core.entity;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Parcelamento {
    private Integer parcela;
    private Integer parcelas;
    private Long codigoPai;

    public Parcelamento(Integer parcela, Integer parcelas, Long codigoPai) {
        this.parcela = parcela;
        this.parcelas = parcelas;
        this.codigoPai = codigoPai;
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

    public Long getCodigoPai() {
        return codigoPai;
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
