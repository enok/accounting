package br.com.accounting.business.exception;

import java.util.List;

public class MissingFieldException extends ValidationException {
    public MissingFieldException(List<String> erros, String erro) {
        super(erros, erro);
    }
}
