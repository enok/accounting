package br.com.accounting.business.exception;

public class MissingFieldException extends BusinessException {
    public MissingFieldException(String mensagem) {
        super(mensagem);
    }
}
