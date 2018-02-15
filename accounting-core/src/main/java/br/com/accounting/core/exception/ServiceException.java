package br.com.accounting.core.exception;

public class ServiceException extends Exception {

    public ServiceException(String mensagem, Exception e) {
        super(mensagem, e);
    }
}
