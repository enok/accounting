package br.com.accounting.commons.exception;

public class GenericException extends Exception {
    public GenericException(Exception e) {
        super(e);
    }
}
