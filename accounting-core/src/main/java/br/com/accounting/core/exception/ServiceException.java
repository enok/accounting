package br.com.accounting.core.exception;

public class ServiceException extends Exception {

    public ServiceException(String message, Exception e) {
        super(message, e);
    }
}
