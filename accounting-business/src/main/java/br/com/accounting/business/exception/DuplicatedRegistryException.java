package br.com.accounting.business.exception;

public class DuplicatedRegistryException extends BusinessException {
    public DuplicatedRegistryException(String message) {
        super(message);
    }
}
