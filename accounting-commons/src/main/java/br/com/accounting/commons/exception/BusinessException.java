package br.com.accounting.commons.exception;

import lombok.Getter;

import java.util.List;

public class BusinessException extends Exception {
    @Getter
    private List<String> erros;

    public BusinessException(List<String> erros, String erro) {
        super(erro);
        this.erros = erros;
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Exception e) {
        super(e);
    }

    public BusinessException(String message, Exception e) {
        super(message, e);
    }
}
