package br.com.accounting.rest.entity;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class Codigos {
    private List<Long> codigos;

    public Codigos(List<Long> codigos) {
        this.codigos = codigos;
    }

    public List<Long> getCodigos() {
        return codigos;
    }

    public Codigos setCodigos(List<Long> codigos) {
        this.codigos = codigos;
        return this;
    }
}
