package br.com.accounting.business.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@Accessors(fluent = true)
public class LocalDTO implements EntityDTO {
    private String codigo;
    private String nome;

    @Override
    public String getCodigo() {
        return codigo;
    }

    public LocalDTO setNome(String nome) {
        this.nome = nome;
        return this;
    }
}
