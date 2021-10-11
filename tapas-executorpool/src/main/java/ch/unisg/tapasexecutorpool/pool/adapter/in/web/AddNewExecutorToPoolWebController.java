package ch.unisg.tapasexecutorpool.pool.adapter.in.web;

import ch.unisg.tapasexecutorpool.pool.application.port.in.AddNewExecutorToPoolCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.AddNewExecutorToPoolUseCase;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;

@RestController
public class AddNewExecutorToPoolWebController {
    private final AddNewExecutorToPoolUseCase addNewExecutorToPoolUseCase;

    public AddNewExecutorToPoolWebController(AddNewExecutorToPoolUseCase addNewExecutorToPoolUseCase) {
        this.addNewExecutorToPoolUseCase = addNewExecutorToPoolUseCase;
    }

    @PostMapping(path = "/executors/", consumes = {ExecutorMediaType.EXECUTOR_MEDIA_TYPE})
    public ResponseEntity<String> addNewExecutorToPool(@RequestBody Executor executor) {
        try {
            AddNewExecutorToPoolCommand command = new AddNewExecutorToPoolCommand(
                    executor.getExecutorName(), executor.getExecutorType()
            );

            Executor newExecutor = addNewExecutorToPoolUseCase.addNewExecutorToPool(command);

            // Add the content type as a response header
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, ExecutorMediaType.EXECUTOR_MEDIA_TYPE);

            return new ResponseEntity<>(ExecutorMediaType.serialize(newExecutor), responseHeaders, HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
