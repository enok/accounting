package br.com.accounting.business.exception;

public class BusinessException extends Exception {
    public BusinessException(String mensagem) {
        super(mensagem);
    }

    public BusinessException(String mensagem, Exception e) {
        super(mensagem, e);
    }
}
