package ch.unisg.tapasexecutors.executors.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;

@RestController
@RequestMapping("digital")
public class DigitalExecutorController {

    private final DigitalExecutorService digitalExecutorService;

    @Autowired
    public DigitalExecutorController(DigitalExecutorService digitalExecutorService) {
        this.digitalExecutorService = digitalExecutorService;
    }

    @PostMapping("runtask")
    public ResponseEntity<String> runTaskInDigitalExecutor(@RequestBody Task task) {
        try {
            System.out.println("Before async call");
            digitalExecutorService.runTaskAsync(task);
            System.out.println("After async call");

            return new ResponseEntity<>("Task is being run!", HttpStatus.ACCEPTED);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
