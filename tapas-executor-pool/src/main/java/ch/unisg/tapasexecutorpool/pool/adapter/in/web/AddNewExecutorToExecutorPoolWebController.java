package ch.unisg.tapasexecutorpool.pool.adapter.in.web;

import ch.unisg.tapasexecutorpool.common.formats.ExecutorJsonRepresentation;
import ch.unisg.tapasexecutorpool.common.formats.NewExecutorJsonRepresentation;
import ch.unisg.tapasexecutorpool.pool.application.port.in.AddNewExecutorToExecutorPoolCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.AddNewExecutorToExecutorPoolUseCase;
import ch.unisg.tapasexecutorpool.pool.application.port.in.ListExecutorsQuery;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import java.util.Collection;

@RestController
public class AddNewExecutorToExecutorPoolWebController {

    @Autowired
    private AddNewExecutorToExecutorPoolUseCase addNewExecutorToExecutorPoolUseCase;

    @Autowired
    private ListExecutorsQuery listExecutorsQuery;


    @PostMapping(path = "/executors/", consumes = {NewExecutorJsonRepresentation.MEDIA_TYPE})
    public ResponseEntity<String> addNewExecutorToExecutorPool(@RequestBody NewExecutorJsonRepresentation payload) {
        try {
            AddNewExecutorToExecutorPoolCommand command = new AddNewExecutorToExecutorPoolCommand(
                    new Executor.ExecutorName(payload.getExecutorName()),
                    new Executor.ExecutorType(payload.getExecutorType()),
                    new Executor.ExecutorUrl(payload.getExecutorUrl())
            );

            Executor newExecutor = addNewExecutorToExecutorPoolUseCase.addNewExecutorToExecutorPool(command);

            // Add the content type as a response header
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, ExecutorJsonRepresentation.MEDIA_TYPE);

            return new ResponseEntity<>(ExecutorJsonRepresentation.serialize(newExecutor), responseHeaders, HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping(path = "/executors/")
    public Collection<Executor> getExecutor(){

        return listExecutorsQuery.listExecutors();
    }
}
