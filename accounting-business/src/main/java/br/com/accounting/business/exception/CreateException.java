package br.com.accounting.business.exception;

import java.util.List;

public class CreateException extends ValidationException {
    public CreateException(List<String> erros, String erro) {
        super(erros, erro);
    }
}
