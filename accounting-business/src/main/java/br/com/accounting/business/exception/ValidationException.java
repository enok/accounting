package br.com.accounting.business.exception;

public class ValidationException extends Exception {
    public ValidationException(Exception e) {
        super(e);
    }
}
