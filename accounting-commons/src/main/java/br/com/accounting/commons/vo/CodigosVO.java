package br.com.accounting.commons.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(fluent = true)
public class CodigosVO {
    private List<Long> codigos;

    public CodigosVO(List<Long> codigos) {
        this.codigos = codigos;
    }

    public List<Long> getCodigos() {
        return codigos;
    }
}
