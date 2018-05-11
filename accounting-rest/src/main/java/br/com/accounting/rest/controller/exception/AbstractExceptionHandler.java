package br.com.accounting.rest.controller.exception;

import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.GenericException;
import br.com.accounting.business.exception.ValidationException;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.rest.vo.ErrorVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class AbstractExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorVO> handleMissingFieldException(ValidationException e) {
        ErrorVO errorVO = new ErrorVO(HttpStatus.BAD_REQUEST.value(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorVO);
    }

    @ExceptionHandler(StoreException.class)
    @ResponseStatus(value = HttpStatus.INSUFFICIENT_STORAGE)
    @ResponseBody
    public ResponseEntity<ErrorVO> handleReadingException(StoreException e) {
        ErrorVO errorVO = new ErrorVO(HttpStatus.INSUFFICIENT_STORAGE.value(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INSUFFICIENT_STORAGE)
                .body(errorVO);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public ResponseEntity<ErrorVO> handleBusinessException(BusinessException e) {
        ErrorVO errorVO = new ErrorVO(HttpStatus.EXPECTATION_FAILED.value(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(errorVO);
    }

    @ExceptionHandler(GenericException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public ResponseEntity<ErrorVO> handleGenericException(GenericException e) {
        ErrorVO errorVO = new ErrorVO(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(errorVO);
    }
}
