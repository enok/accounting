package br.com.accounting.local.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class LocalVO {
    private String codigo;
    private String nome;

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public LocalVO setCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public LocalVO setNome(String nome) {
        this.nome = nome;
        return this;
    }
}
