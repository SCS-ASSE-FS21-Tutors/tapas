package ch.unisg.tapasadditionexecutor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AddNumbersController {
    private final AddNumbersUseCase addNumbersUseCase;

    public AddNumbersController(AddNumbersUseCase addNumbersUseCase) {
        this.addNumbersUseCase = addNumbersUseCase;
    }

    @PostMapping(path = "/addition", consumes = "application/json")
    public ResponseEntity<Float> addNumbers(@RequestBody AddNumbersCommand addNumbersCommand) {
        Float result = addNumbersUseCase.addNumbers(addNumbersCommand);
        // Add the content type as a response header
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return new ResponseEntity<Float>(result, responseHeaders, HttpStatus.CREATED);

    }
}
