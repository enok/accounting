package br.com.accounting.commons.exception;

public class ServiceException extends Exception {
    public ServiceException(String message, Exception e) {
        super(message, e);
    }
}
