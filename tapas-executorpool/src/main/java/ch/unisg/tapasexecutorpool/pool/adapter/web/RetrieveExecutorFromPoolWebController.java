package ch.unisg.tapasexecutorpool.pool.adapter.web;

import ch.unisg.tapasexecutorpool.pool.application.port.in.RetrieveExecutorFromPoolCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.RetrieveExecutorFromPoolUseCase;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class RetrieveExecutorFromPoolWebController {
    private final RetrieveExecutorFromPoolUseCase retrieveExecutorFromPoolUseCase;

    public RetrieveExecutorFromPoolWebController(RetrieveExecutorFromPoolUseCase retrieveExecutorFromPoolUseCase) {
        this.retrieveExecutorFromPoolUseCase = retrieveExecutorFromPoolUseCase;
    }

    @GetMapping(path = "/executors/{executorId}")
    public ResponseEntity<String> retrieveTaskFromTaskList(@PathVariable("executorId") String executor) {
        RetrieveExecutorFromPoolCommand command = new RetrieveExecutorFromPoolCommand(new Executor.ExecutorId(executor));
        Optional<Executor> updatedTaskOpt = retrieveExecutorFromPoolUseCase.retrieveExecutorFromPool(command);

        // Check if the task with the given identifier exists
        if (updatedTaskOpt.isEmpty()) {
            // If not, through a 404 Not Found status code
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // Add the content type as a response header
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, ExecutorMediaType.EXECUTOR_MEDIA_TYPE);

        return new ResponseEntity<>(ExecutorMediaType.serialize(updatedTaskOpt.get()), responseHeaders,
                HttpStatus.OK);
    }
}
