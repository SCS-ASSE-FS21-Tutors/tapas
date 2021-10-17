package ch.unisg.assignment.assignment.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ch.unisg.assignment.common.exception.ErrorResponse;
import ch.unisg.assignment.common.exception.InvalidIP4Exception;
import ch.unisg.assignment.common.exception.PortOutOfRangeException;

@ControllerAdvice
public class WebControllerExceptionHandler {

    @ExceptionHandler(PortOutOfRangeException.class)
    public ResponseEntity<ErrorResponse> handleException(PortOutOfRangeException e){

        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        return new ResponseEntity<>(error, error.getHttpStatus());

     }

     @ExceptionHandler(InvalidIP4Exception.class)
     public ResponseEntity<ErrorResponse> handleException(InvalidIP4Exception e){

         ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
         return new ResponseEntity<>(error, error.getHttpStatus());

      }

}
