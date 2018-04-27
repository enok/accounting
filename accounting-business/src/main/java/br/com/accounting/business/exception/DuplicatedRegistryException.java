package br.com.accounting.business.exception;

public class DuplicatedRegistryException extends ValidationException {
    public DuplicatedRegistryException(String message) {
        super(message);
    }
}
