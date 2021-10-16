package ch.unisg.tapasexecutorcherrybot.cherrybot;

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
@RequestMapping("cherrybot")
public class CherrybotExecutorController {

    private final CherrybotExecutorService cherrybotExecutorService;

    @Autowired
    public CherrybotExecutorController(CherrybotExecutorService cherrybotExecutorService) {
        this.cherrybotExecutorService = cherrybotExecutorService;
    }

    @PostMapping("runtask")
    public ResponseEntity<String> runTaskInDigitalExecutor(@RequestBody Task task) {
        try {
            cherrybotExecutorService.runTaskAsync(task);

            return new ResponseEntity<>("Task is being run!", HttpStatus.ACCEPTED);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
