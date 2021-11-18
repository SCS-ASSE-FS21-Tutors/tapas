package ch.unisg.tapas.example;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;

@RestController
@Log4j2
public class CalculatorController {


    @PostMapping(path = "/execute/")
    public ResponseEntity<String> startCalculation(@RequestBody Calculation calculation) {
        try {

            log.info("Received calculation with input data: "+ calculation.getInputData());
            // Add the content type as a response header
            HttpHeaders responseHeaders = new HttpHeaders();

            CalculationThread calculationThread = new CalculationThread(calculation);
            calculationThread.start();

            return new ResponseEntity<>("Task Started", responseHeaders, HttpStatus.ACCEPTED);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
