package br.com.accounting.business.exception;

import java.util.List;

public class ValidationException extends BusinessException {
    public ValidationException(List<String> erros, String erro) {
        super(erros, erro);
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Exception e) {
        super(message, e);
    }

    public ValidationException(Exception e) {
        super(e);
    }
}
