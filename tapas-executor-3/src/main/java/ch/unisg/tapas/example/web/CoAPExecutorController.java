package ch.unisg.tapas.example.web;

import ch.unisg.tapas.example.application.CoAPRequestThread;
import ch.unisg.tapas.example.formats.TaskJsonRepresentation;
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

@RestController
@Log4j2
public class CoAPExecutorController {

    @Value("${executor.pool.uri}")
    private String executorPoolUri;

    @Value("${sparql.search.engine.uri}")
    private String sparqlSearchEngineUri;


    @PostMapping(path = "/execute/", consumes = TaskJsonRepresentation.MEDIA_TYPE)
    public ResponseEntity<String> executeTask(@RequestBody TaskJsonRepresentation task) {
        try {

            log.info("Received calculation with task id: " + task.getTaskId());
            HttpHeaders responseHeaders = new HttpHeaders();

            CoAPRequestThread coAPRequestThread = new CoAPRequestThread(
                    task,
                    sparqlSearchEngineUri,
                    executorPoolUri);
            coAPRequestThread.start();

            return new ResponseEntity<>("Task Started", responseHeaders, HttpStatus.ACCEPTED);

        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
