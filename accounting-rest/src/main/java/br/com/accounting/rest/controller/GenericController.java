package br.com.accounting.rest.controller;

import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.GenericException;
import br.com.accounting.business.exception.ValidationException;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.rest.entity.Error;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class GenericController {
    protected abstract Logger getLog();

    protected abstract String getMensagem();

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ResponseEntity handleMissingFieldException(ValidationException e) {
        getLog().error(getMensagem(), e);
        Error error = new Error(HttpStatus.BAD_REQUEST.value(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(StoreException.class)
    @ResponseStatus(value = HttpStatus.INSUFFICIENT_STORAGE)
    public @ResponseBody
    ResponseEntity handleReadingException(StoreException e) {
        getLog().error(getMensagem(), e);
        Error error = new Error(HttpStatus.INSUFFICIENT_STORAGE.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE).body(error);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(value = HttpStatus.INSUFFICIENT_STORAGE)
    public @ResponseBody
    ResponseEntity handleBusinessException(BusinessException e) {
        getLog().error(getMensagem(), e);
        Error error = new Error(HttpStatus.EXPECTATION_FAILED.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(error);
    }

    @ExceptionHandler(GenericException.class)
    @ResponseStatus(value = HttpStatus.INSUFFICIENT_STORAGE)
    public @ResponseBody
    ResponseEntity handleGenericException(GenericException e) {
        getLog().error(getMensagem(), e);
        Error error = new Error(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }
}
