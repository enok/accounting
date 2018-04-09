package br.com.accounting.business.exception;

import java.util.List;

public class UpdateException extends BusinessException {
    public UpdateException(List<String> erros, String erro) {
        super(erros, erro);
    }
}
