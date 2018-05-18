package br.com.accounting.commons.exception;

import java.util.List;

public class CreateException extends ValidationException {
    public CreateException(List<String> erros, String erro) {
        super(erros, erro);
    }
}
