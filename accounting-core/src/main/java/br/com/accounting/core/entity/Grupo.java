package br.com.accounting.core.entity;

import br.com.accounting.commons.entity.Entity;
import br.com.accounting.commons.entity.SubGrupo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Accessors(fluent = true)
public class Grupo implements Entity, Serializable {
    private Long codigo;
    private String nome;
    private String descricao;
    private List<SubGrupo> subGrupos = new ArrayList<>();

    @Override
    public Long getCodigo() {
        return codigo;
    }

    @Override
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public void addSubGrupo(String nomeSubGrupo) {
        this.subGrupos.add(criarSubGrupo(nomeSubGrupo));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Grupo grupo = (Grupo) o;

        return new EqualsBuilder()
                .append(nome, grupo.nome)
                .isEquals();
    }

    private SubGrupo criarSubGrupo(String nomeSubGrupo) {
        return new SubGrupo()
                .nome(nomeSubGrupo);
    }
}
