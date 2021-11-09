package ch.unisg.executorpool.adapter.in.web;

import ch.unisg.executorpool.adapter.common.formats.ExecutorJsonRepresentation;
import ch.unisg.executorpool.application.port.in.GetAllExecutorsInExecutorPoolByTypeQuery;
import ch.unisg.executorpool.application.port.in.GetAllExecutorsInExecutorPoolByTypeUseCase;
import ch.unisg.executorpool.domain.ExecutorClass;
import ch.unisg.executorpool.domain.ExecutorClass.ExecutorTaskType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllExecutorsInExecutorPoolByTypeWebController {
    private final GetAllExecutorsInExecutorPoolByTypeUseCase getAllExecutorsInExecutorPoolByTypeUseCase;

    public GetAllExecutorsInExecutorPoolByTypeWebController(GetAllExecutorsInExecutorPoolByTypeUseCase getAllExecutorInExecutorPoolByTypeUseCase){
        this.getAllExecutorsInExecutorPoolByTypeUseCase = getAllExecutorInExecutorPoolByTypeUseCase;
    }

    @GetMapping(path = "/executor-pool/GetAllExecutorsInExecutorPoolByType/{taskType}")
    public ResponseEntity<String> getAllExecutorInExecutorPoolByType(@PathVariable("taskType") String taskType){
        GetAllExecutorsInExecutorPoolByTypeQuery query = new GetAllExecutorsInExecutorPoolByTypeQuery(new ExecutorTaskType(taskType));
        List<ExecutorClass> matchedExecutors = getAllExecutorsInExecutorPoolByTypeUseCase.getAllExecutorsInExecutorPoolByType(query);

        // Add the content type as a response header
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, ExecutorJsonRepresentation.EXECUTOR_MEDIA_TYPE);

        return new ResponseEntity<>(ExecutorJsonRepresentation.serialize(matchedExecutors), responseHeaders, HttpStatus.OK);
    }
}
