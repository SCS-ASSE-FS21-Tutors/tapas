package ch.unisg.executorpool.adapter.in.web;

import ch.unisg.executorpool.application.port.in.AddNewExecutorToExecutorPoolUseCase;
import ch.unisg.executorpool.application.port.in.AddNewExecutorToExecutorPoolCommand;
import ch.unisg.executorpool.domain.ExecutorClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.ConstraintViolationException;

@RestController
public class AddNewExecutorToExecutorPoolWebController {
    private final AddNewExecutorToExecutorPoolUseCase addNewExecutorToExecutorPoolUseCase;

    public AddNewExecutorToExecutorPoolWebController(AddNewExecutorToExecutorPoolUseCase addNewExecutorToExecutorPoolUseCase){
        this.addNewExecutorToExecutorPoolUseCase = addNewExecutorToExecutorPoolUseCase;
    }

    @PostMapping(path = "/executor-pool/AddExecutor", consumes = {ExecutorMediaType.EXECUTOR_MEDIA_TYPE})
    public ResponseEntity<String> addNewExecutorToExecutorPool(@RequestBody ExecutorClass executorClass){
        try{
            AddNewExecutorToExecutorPoolCommand command = new AddNewExecutorToExecutorPoolCommand(
                    executorClass.getExecutorIp(), executorClass.getExecutorPort(), executorClass.getExecutorTaskType()
            );

            ExecutorClass newExecutor = addNewExecutorToExecutorPoolUseCase.addNewExecutorToExecutorPool(command);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, ExecutorMediaType.EXECUTOR_MEDIA_TYPE);

            return new ResponseEntity<>(ExecutorMediaType.serialize(newExecutor), responseHeaders, HttpStatus.CREATED);
        } catch (ConstraintViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
