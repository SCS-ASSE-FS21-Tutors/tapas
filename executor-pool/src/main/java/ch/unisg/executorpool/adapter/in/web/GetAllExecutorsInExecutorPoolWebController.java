package ch.unisg.executorpool.adapter.in.web;

import ch.unisg.executorpool.adapter.common.formats.ExecutorJsonRepresentation;
import ch.unisg.executorpool.application.port.in.GetAllExecutorsInExecutorPoolUseCase;
import ch.unisg.executorpool.domain.ExecutorClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllExecutorsInExecutorPoolWebController {
    private final GetAllExecutorsInExecutorPoolUseCase getAllExecutorsInExecutorPoolUseCase;

    public GetAllExecutorsInExecutorPoolWebController(GetAllExecutorsInExecutorPoolUseCase getAllExecutorsInExecutorPoolUseCase){
        this.getAllExecutorsInExecutorPoolUseCase = getAllExecutorsInExecutorPoolUseCase;
    }

    @GetMapping(path = "executor-pool/GetAllExecutorsinExecutorPool")
    public ResponseEntity<String> getAllExecutorsInExecutorPool(){
        List<ExecutorClass> executorClassList = getAllExecutorsInExecutorPoolUseCase.getAllExecutorsInExecutorPool();

        // Add the content type as a response header
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, ExecutorJsonRepresentation.EXECUTOR_MEDIA_TYPE);

        return new ResponseEntity<>(ExecutorJsonRepresentation.serialize(executorClassList), responseHeaders, HttpStatus.OK);
    }
}
