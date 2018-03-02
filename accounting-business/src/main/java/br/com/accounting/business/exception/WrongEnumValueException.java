package br.com.accounting.business.exception;

public class WrongEnumValueException extends BusinessException {
    public WrongEnumValueException(String message, Exception e) {
        super(message, e);
    }
}
