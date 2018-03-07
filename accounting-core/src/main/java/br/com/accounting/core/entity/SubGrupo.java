package br.com.accounting.core.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class SubGrupo implements Entity, Serializable {
    private Long codigo;
    private String descricao;

    public SubGrupo() {
    }

    public SubGrupo(Long codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Long getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public SubGrupo withCodigo(Long codigo) {
        this.codigo = codigo;
        return this;
    }

    public SubGrupo withDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        SubGrupo subGrupo = (SubGrupo) o;

        return new EqualsBuilder()
                .append(descricao, subGrupo.descricao)
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
