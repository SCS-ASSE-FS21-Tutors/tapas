package ch.unisg.tapasexecutorpool.executorpool.adapter.in.web;

import ch.unisg.tapasexecutorpool.executorpool.application.port.in.AddExecutorToPoolCommand;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.AddExecutorToPoolUseCase;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import org.json.JSONException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;

@RestController
public class AddExecutorToPoolWebController {
    private final AddExecutorToPoolUseCase addExecutorToPoolUseCase;

    public AddExecutorToPoolWebController(AddExecutorToPoolUseCase addExecutorToPoolUseCase) {
        this.addExecutorToPoolUseCase = addExecutorToPoolUseCase;
    }

    @PostMapping(path = "/executors/", consumes = {ExecutorMediaType.EXECUTOR_MEDIA_TYPE})
    public ResponseEntity<String> addNewExecutorToExecutorPool(@RequestBody Executor executor) {
        try {
            AddExecutorToPoolCommand command = new AddExecutorToPoolCommand(
                    executor.getExecutorUrl(), executor.getExecutorType()
            );

            Executor newExecutor = addExecutorToPoolUseCase.addNewExecutorToExecutorPool(command);

            // Add the content type as a response header
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, ExecutorMediaType.EXECUTOR_MEDIA_TYPE);

            return new ResponseEntity<>(ExecutorMediaType.serialize(newExecutor), responseHeaders, HttpStatus.CREATED);
        } catch (ConstraintViolationException | JSONException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
