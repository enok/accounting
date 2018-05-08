package br.com.accounting.rest.vo;

import java.util.List;

public class CodigosVO {
    private List<Long> codigos;

    public CodigosVO(List<Long> codigos) {
        this.codigos = codigos;
    }

    public List<Long> getCodigos() {
        return codigos;
    }
}
