package ch.unisg.tapasexecutorpool.executorpool.adapter.in.web;

import ch.unisg.tapasexecutorpool.executorpool.application.port.in.RetrieveExecutorByIdQuery;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.RetrieveExecutorByIdUseCase;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import org.json.JSONException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class RetrieveExecutorByIdWebController {
    private final RetrieveExecutorByIdUseCase retrieveExecutorByIdUseCase;

    public RetrieveExecutorByIdWebController(RetrieveExecutorByIdUseCase retrieveExecutorByIdUseCase) {
        this.retrieveExecutorByIdUseCase = retrieveExecutorByIdUseCase;
    }

    @GetMapping(path = "/executors/{executorId}")
    public ResponseEntity<String> retrieveExecutorFromExecutorPool(@PathVariable("executorId") String executorId) {
        RetrieveExecutorByIdQuery query = new RetrieveExecutorByIdQuery(new Executor.ExecutorId(executorId));
        Optional<Executor> updatedExecutorOpt = retrieveExecutorByIdUseCase.retrieveExecutorById(query);

        // Check if the task with the given identifier exists
        if (updatedExecutorOpt.isEmpty()) {
            // If not, through a 404 Not Found status code
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // Add the content type as a response header
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, ExecutorMediaType.EXECUTOR_MEDIA_TYPE);

        try {
            return new ResponseEntity<>(ExecutorMediaType.serialize(updatedExecutorOpt.get()), responseHeaders,
                    HttpStatus.OK);
        } catch (JSONException e) {
            System.out.println("There was an error retrieving the Executor");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}