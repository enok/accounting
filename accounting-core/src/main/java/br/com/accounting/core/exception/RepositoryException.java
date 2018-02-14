package br.com.accounting.core.exception;

import java.io.IOException;

public class RepositoryException extends Throwable {

    public RepositoryException(String message, IOException e) {
        super(message, e);
    }
}
