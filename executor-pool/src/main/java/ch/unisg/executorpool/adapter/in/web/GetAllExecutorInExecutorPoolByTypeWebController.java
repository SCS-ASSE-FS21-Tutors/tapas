package ch.unisg.executorpool.adapter.in.web;

import ch.unisg.executorpool.application.port.in.GetAllExecutorInExecutorPoolByTypeQuery;
import ch.unisg.executorpool.application.port.in.GetAllExecutorInExecutorPoolByTypeUseCase;
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
public class GetAllExecutorInExecutorPoolByTypeWebController {
    private final GetAllExecutorInExecutorPoolByTypeUseCase getAllExecutorInExecutorPoolByTypeUseCase;

    public GetAllExecutorInExecutorPoolByTypeWebController(GetAllExecutorInExecutorPoolByTypeUseCase getAllExecutorInExecutorPoolByTypeUseCase){
        this.getAllExecutorInExecutorPoolByTypeUseCase = getAllExecutorInExecutorPoolByTypeUseCase;
    }

    @GetMapping(path = "/executor-pool/GetAllExecutorInExecutorPoolByType/{taskType}")
    public ResponseEntity<String> getAllExecutorInExecutorPoolByType(@PathVariable("taskType") String taskType){
        GetAllExecutorInExecutorPoolByTypeQuery query = new GetAllExecutorInExecutorPoolByTypeQuery(new ExecutorTaskType(taskType));
        List<ExecutorClass> matchedExecutors = getAllExecutorInExecutorPoolByTypeUseCase.getAllExecutorInExecutorPoolByType(query);

        // Add the content type as a response header
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, ExecutorMediaType.EXECUTOR_MEDIA_TYPE);

        return new ResponseEntity<>(ExecutorMediaType.serialize(matchedExecutors), responseHeaders, HttpStatus.OK);
    }
}
