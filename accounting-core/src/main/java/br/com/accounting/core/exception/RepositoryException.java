package br.com.accounting.core.exception;

public class RepositoryException extends Exception {
    public RepositoryException(String message, Exception e) {
        super(message, e);
    }
}
