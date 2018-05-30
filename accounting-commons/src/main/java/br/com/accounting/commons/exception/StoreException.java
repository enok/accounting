package br.com.accounting.commons.exception;

public class StoreException extends Exception {
    public StoreException(String message) {
        super(message);
    }

    public StoreException(String message, Exception e) {
        super(message, e);
    }
}
