package ch.unisg.tapas.example.adapter;

import ch.unisg.tapas.example.application.port.CalculatorCommand;
import ch.unisg.tapas.example.application.port.CalculatorUseCase;
import ch.unisg.tapas.example.domain.Calculator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;

@RestController
//@RequestMapping("/calculator")

public class CalculatorController {
    private final CalculatorUseCase calculatorUseCase;

    public CalculatorController(CalculatorUseCase calculatorUseCase){
        this.calculatorUseCase = calculatorUseCase;
    }

    @PostMapping(path = "/calculator/start/", consumes = {CalculatorMediaType.TASK_MEDIA_TYPE})
    public ResponseEntity<String> executeCalculator(@RequestBody Calculator calculator) {
        try {
            CalculatorCommand command = new CalculatorCommand(
                    calculator.getInputValue(), calculator.getInputOperator()
            );

            Calculator newCalculator = calculatorUseCase.executeCalculator(command);

            // Add the content type as a response header
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, CalculatorMediaType.TASK_MEDIA_TYPE);

            return new ResponseEntity<>(CalculatorMediaType.serialize(newCalculator), responseHeaders, HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping(path = "/calculator/complete/", consumes = {CalculatorMediaType.TASK_MEDIA_TYPE})
    public ResponseEntity<String> completeCalculator(@RequestBody Calculator calculator) {
        try {
            CalculatorCommand command = new CalculatorCommand(
                    calculator.getInputValue(), calculator.getInputOperator()
            );

            Calculator newCalculator = calculatorUseCase.completeCalculator(command);

            // Add the content type as a response header
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, CalculatorMediaType.TASK_MEDIA_TYPE);

            return new ResponseEntity<>(CalculatorMediaType.serialize(newCalculator), responseHeaders, HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
