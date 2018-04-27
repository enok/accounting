package br.com.accounting.business.exception;

public class GenericException extends Exception {
    public GenericException(Exception e) {
        super(e);
    }
}
