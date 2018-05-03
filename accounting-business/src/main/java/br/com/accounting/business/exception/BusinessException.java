package br.com.accounting.business.exception;

import lombok.Getter;

import java.util.List;

public class BusinessException extends Exception {
    @Getter
    private List<String> erros;

    public BusinessException(List<String> erros) {
        this.erros = erros;
    }

    public BusinessException(List<String> erros, String erro) {
        super(erro);
        this.erros = erros;
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Exception e) {
        super(e);
        if (e instanceof ValidationException) {
            ValidationException e1 = (ValidationException) e;
            if ((e1 instanceof MissingFieldException) || (e1 instanceof CreateException)) {
                this.erros = e1.getErros();
            }
        }
    }

    public BusinessException(String message, Exception e) {
        super(message, e);
    }
}
