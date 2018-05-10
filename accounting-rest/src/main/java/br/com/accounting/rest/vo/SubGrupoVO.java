package br.com.accounting.rest.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class SubGrupoVO {
    private String codigo;
    private String nome;
    private String descricao;

    public SubGrupoVO setCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public SubGrupoVO setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public SubGrupoVO setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }
}
