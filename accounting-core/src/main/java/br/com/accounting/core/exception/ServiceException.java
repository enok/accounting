package br.com.accounting.core.exception;

public class ServiceException extends Throwable {

    public ServiceException(String mensagem, RepositoryException e) {
        super(mensagem, e);
    }
}
