package ch.unisg.assignment.assignment.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ch.unisg.common.exception.ErrorResponse;
import ch.unisg.common.exception.InvalidExecutorURIException;

@ControllerAdvice
public class WebControllerExceptionHandler {

    @ExceptionHandler(InvalidExecutorURIException.class)
    public ResponseEntity<ErrorResponse> handleException(InvalidExecutorURIException e){

        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        return new ResponseEntity<>(error, error.getHttpStatus());

     }
}
