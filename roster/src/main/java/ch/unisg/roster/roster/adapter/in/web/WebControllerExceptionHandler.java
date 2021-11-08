package ch.unisg.roster.roster.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ch.unisg.common.exception.ErrorResponse;
import ch.unisg.common.exception.InvalidExecutorURIException;

@ControllerAdvice
public class WebControllerExceptionHandler {

    /**
    *   Handles error of type InvalidExecutorURIException
    *   @return 404 Bad Request
    **/
    @ExceptionHandler(InvalidExecutorURIException.class)
    public ResponseEntity<ErrorResponse> handleException(InvalidExecutorURIException e){

        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        return new ResponseEntity<>(error, error.getHttpStatus());

     }
}
