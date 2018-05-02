package br.com.accounting.business.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@Accessors(fluent = true)
public class SubGrupoDTO implements EntityDTO {
    private String codigo;
    private String nome;
    private String descricao;

    @Override
    public String getCodigo() {
        return codigo;
    }

    public SubGrupoDTO setCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public SubGrupoDTO setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public SubGrupoDTO setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }
}
