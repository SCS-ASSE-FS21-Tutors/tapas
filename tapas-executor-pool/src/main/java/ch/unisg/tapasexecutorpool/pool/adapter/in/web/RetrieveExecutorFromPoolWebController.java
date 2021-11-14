package ch.unisg.tapasexecutorpool.pool.adapter.in.web;

import ch.unisg.tapasexecutorpool.pool.adapter.in.formats.ExecutorJsonRepresentation;
import ch.unisg.tapasexecutorpool.pool.application.port.in.RetrieveExecutorFromPoolCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.RetrieveExecutorFromPoolUseCase;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RetrieveExecutorFromPoolWebController {
    private final RetrieveExecutorFromPoolUseCase retrieveExecutorFromPoolUseCase;

    @GetMapping(path = "/executors/{executorId}")
    public ResponseEntity<String> retrieveTaskFromTaskList(@PathVariable("executorId") String executor) {
        var command = new RetrieveExecutorFromPoolCommand(new Executor.ExecutorId(executor));
        var executorOptional = retrieveExecutorFromPoolUseCase.retrieveExecutorFromPool(command);

        if (executorOptional.isPresent()) {
            var responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, ExecutorJsonRepresentation.EXECUTOR_MEDIA_TYPE);
            try {
                var json = executorOptional.get().serialize();
                return new ResponseEntity<>(json, responseHeaders, HttpStatus.OK);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
