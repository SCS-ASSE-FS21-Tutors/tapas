package ch.unisg.assignment.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorResponse {
    private final HttpStatus httpStatus;
    private final String message;
}
