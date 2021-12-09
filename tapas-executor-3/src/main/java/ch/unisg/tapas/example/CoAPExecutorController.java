package ch.unisg.tapas.example;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Log4j2
public class CoAPExecutorController {

    public static final List<String> POSSIBLE_INPUT = new ArrayList<>() {
        {
            add("temperature");
            add("humidity");
        }
    };

    @Value("${executor.pool.uri}")
    private String executorPoolUri;

    @Value("${sparql.search.engine.uri}")
    private String sparqlSearchEngineUri;


    @PostMapping(path = "/execute/")
    public ResponseEntity<String> startCalculation(@RequestBody LeanTaskDto payload) {
        try {
            String taskId = payload.getTaskId();
            String taskInput = payload.getTaskInput();

            log.info("Received calculation with task id: " + taskId +
                    " | input data: " + taskInput);
            HttpHeaders responseHeaders = new HttpHeaders();

            if (POSSIBLE_INPUT.contains(taskInput)) {
                CoAPRequestThread coAPRequestThread = new CoAPRequestThread(
                        taskId,
                        taskInput,
                        sparqlSearchEngineUri,
                        executorPoolUri);
                coAPRequestThread.start();
            } else {
                return new ResponseEntity<>("Can only process input of type(s): " + POSSIBLE_INPUT.toString(),
                        responseHeaders, HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>("Task Started", responseHeaders, HttpStatus.ACCEPTED);

        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
