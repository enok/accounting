package br.com.accounting.rest.vo;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class CodigosVO {
    private List<Long> codigos;

    public CodigosVO(List<Long> codigos) {
        this.codigos = codigos;
    }

    public List<Long> getCodigos() {
        return codigos;
    }

    public CodigosVO setCodigos(List<Long> codigos) {
        this.codigos = codigos;
        return this;
    }
}
