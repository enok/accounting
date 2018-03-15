package br.com.accounting.business.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class BusinessException extends Exception {
    @Getter
    private List<String> erros;

    public BusinessException(List<String> erros) {
        this.erros = erros;
    }
}
