package ch.unisg.tapasexecutors.executors.domain;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class DigitalExecutorController {

    private final DigitalExecutor digitalExecutor;

    public DigitalExecutorController(DigitalExecutor digitalExecutor) {
        this.digitalExecutor = digitalExecutor;
    }

    @PostMapping("/executors/digital/")
    public ResponseEntity<DigitalExecutor> executeRobotExecutor(@RequestBody DigitalExecutor digitalExecutor) {
        if (digitalExecutor == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        else {
            return new ResponseEntity<>(digitalExecutor, HttpStatus.CREATED);
        }
    }
}
