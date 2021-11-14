package ch.unisg.executorpool.adapter.in.web;

import ch.unisg.executorpool.adapter.common.formats.ExecutorJsonRepresentation;
import ch.unisg.executorpool.application.port.in.RemoveExecutorFromExecutorPoolCommand;
import ch.unisg.executorpool.application.port.in.RemoveExecutorFromExecutorPoolUseCase;
import ch.unisg.executorpool.domain.ExecutorClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.Optional;

@RestController
public class RemoveExecutorFromExecutorPoolWebController {
    private final RemoveExecutorFromExecutorPoolUseCase removeExecutorFromExecutorPoolUseCase;

    public RemoveExecutorFromExecutorPoolWebController(RemoveExecutorFromExecutorPoolUseCase removeExecutorFromExecutorPoolUseCase){
        this.removeExecutorFromExecutorPoolUseCase = removeExecutorFromExecutorPoolUseCase;
    }

    @PostMapping(path = "/executor-pool/RemoveExecutor", consumes = {ExecutorJsonRepresentation.EXECUTOR_MEDIA_TYPE})
    public ResponseEntity<String> removeExecutorFromExecutorPool(@RequestBody ExecutorJsonRepresentation executorJsonRepresentation){
        RemoveExecutorFromExecutorPoolCommand command = new RemoveExecutorFromExecutorPoolCommand(
            new ExecutorClass.ExecutorUri(URI.create(executorJsonRepresentation.getExecutorUri()))
        );
        Optional<ExecutorClass> removedExecutor = removeExecutorFromExecutorPoolUseCase.removeExecutorFromExecutorPool(command);

        if(removedExecutor.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, ExecutorJsonRepresentation.EXECUTOR_MEDIA_TYPE);

        return new ResponseEntity<>(ExecutorJsonRepresentation.serialize(removedExecutor.get()), responseHeaders,
                HttpStatus.OK);
    }
}
