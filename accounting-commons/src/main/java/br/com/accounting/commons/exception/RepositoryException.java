package br.com.accounting.commons.exception;

public class RepositoryException extends Exception {
    public RepositoryException(String message, Exception e) {
        super(message, e);
    }
}
