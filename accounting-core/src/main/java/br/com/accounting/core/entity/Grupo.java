package br.com.accounting.core.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Grupo implements Entity {
    private Long codigo;
    private String descricao;
    private SubGrupo subGrupo;

    public Grupo() {
    }

    public Long getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public SubGrupo getSubGrupo() {
        return subGrupo;
    }

    public String getSubGrupoDescricao() {
        return subGrupo.getDescricao();
    }

    public Grupo withCodigo(Long codigo) {
        this.codigo = codigo;
        return this;
    }

    public Grupo withDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public Grupo withSubGrupo(SubGrupo subGrupo) {
        this.subGrupo = subGrupo;
        return this;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Grupo grupo = (Grupo) o;
//
//        return new EqualsBuilder()
//                .append(descricao, grupo.descricao)
//                .append(subGrupo, grupo.subGrupo)
//                .isEquals();
//    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(descricao)
                .append(subGrupo)
                .toHashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
