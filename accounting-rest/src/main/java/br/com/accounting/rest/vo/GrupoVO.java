package br.com.accounting.rest.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(fluent = true)
public class GrupoVO {
    private String codigo;
    private String nome;
    private String descricao;
    private List<String> subGrupos = new ArrayList<>();

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<String> getSubGrupos() {
        return subGrupos;
    }

    public GrupoVO setCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public GrupoVO setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public GrupoVO setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public GrupoVO setSubGrupos(List<String> subGrupos) {
        this.subGrupos = subGrupos;
        return this;
    }
}
