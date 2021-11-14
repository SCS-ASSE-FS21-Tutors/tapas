package ch.unisg.executorpool.adapter.in.web;

import ch.unisg.executorpool.adapter.common.clients.TapasMqttClient;
import ch.unisg.executorpool.adapter.common.formats.ExecutorJsonRepresentation;
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
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.*;

@RestController
public class AddNewExecutorToExecutorPoolWebController {
    private final AddNewExecutorToExecutorPoolUseCase addNewExecutorToExecutorPoolUseCase;

    public AddNewExecutorToExecutorPoolWebController(AddNewExecutorToExecutorPoolUseCase addNewExecutorToExecutorPoolUseCase){
        this.addNewExecutorToExecutorPoolUseCase = addNewExecutorToExecutorPoolUseCase;
    }

    @PostMapping(path = "/executor-pool/AddExecutor", consumes = {ExecutorJsonRepresentation.EXECUTOR_MEDIA_TYPE})
    public ResponseEntity<String> addNewExecutorToExecutorPool(@RequestBody ExecutorJsonRepresentation payload){
        try {
            AddNewExecutorToExecutorPoolCommand command = new AddNewExecutorToExecutorPoolCommand(
                new ExecutorClass.ExecutorUri(URI.create(payload.getExecutorUri())),
                new ExecutorClass.ExecutorTaskType(payload.getExecutorTaskType())
            );

            ExecutorClass newExecutor = addNewExecutorToExecutorPoolUseCase.addNewExecutorToExecutorPool(command);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, ExecutorJsonRepresentation.EXECUTOR_MEDIA_TYPE);

            return new ResponseEntity<>(ExecutorJsonRepresentation.serialize(newExecutor), responseHeaders, HttpStatus.CREATED);

        } catch (ConstraintViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
