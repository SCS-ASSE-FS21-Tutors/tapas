package ch.unisg.tapasexecutorpool.pool.adapter.in.web;

import ch.unisg.tapasexecutorpool.pool.adapter.in.formats.ExecutorJsonRepresentation;
import ch.unisg.tapasexecutorpool.pool.application.port.in.AddNewExecutorToPoolCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.AddNewExecutorToPoolUseCase;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;

@RequiredArgsConstructor
@RestController
public class AddNewExecutorToPoolWebController {
    private final AddNewExecutorToPoolUseCase addNewExecutorToPoolUseCase;

    @PostMapping(path = "/executors/", consumes = {ExecutorMediaType.EXECUTOR_MEDIA_TYPE})
    public ResponseEntity<String> addNewExecutorToPool(@RequestBody ExecutorJsonRepresentation executorJsonRepresentation) {
        try {
            var command = new AddNewExecutorToPoolCommand(executorJsonRepresentation);
            var newExecutorRepresentation = addNewExecutorToPoolUseCase.addNewExecutorToPool(command);
            var responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, ExecutorMediaType.EXECUTOR_MEDIA_TYPE);
            return new ResponseEntity<>(newExecutorRepresentation.serialize(), responseHeaders, HttpStatus.CREATED);
        } catch (ConstraintViolationException | JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
