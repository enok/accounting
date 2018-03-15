package br.com.accounting.business.exception;

import java.util.List;

public class MissingFieldException extends BusinessException {
    public MissingFieldException(List<String> erros) {
        super(erros);
    }
}
