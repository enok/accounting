package br.com.accounting.business.exception;

public class TechnicalException extends Exception {
    public TechnicalException(String mensagem, Exception e) {
        super(mensagem, e);
    }
}
