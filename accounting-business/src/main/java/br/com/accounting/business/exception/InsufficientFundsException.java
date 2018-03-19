package br.com.accounting.business.exception;

public class InsufficientFundsException extends BusinessException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
