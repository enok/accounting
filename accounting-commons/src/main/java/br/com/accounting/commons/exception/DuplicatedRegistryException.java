package br.com.accounting.commons.exception;

public class DuplicatedRegistryException extends ValidationException {
    public DuplicatedRegistryException(String message) {
        super(message);
    }
}
