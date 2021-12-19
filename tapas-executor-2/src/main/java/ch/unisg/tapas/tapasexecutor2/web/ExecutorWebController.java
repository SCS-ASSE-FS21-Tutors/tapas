package ch.unisg.tapas.tapasexecutor2.web;

import ch.unisg.tapas.tapasexecutor2.application.ExecutorService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
public class ExecutorWebController {

    @Autowired
    ExecutorService executorService;

    @PostMapping(path = "/execute", consumes = TaskJsonRepresentation.MEDIA_TYPE)
    public ResponseEntity executeTask(@RequestBody TaskJsonRepresentation taskJsonRepresentation) {

        if (!"COMPUTATION".equals(taskJsonRepresentation.getTaskType()) && !"COMPUTATION_DEMO".equals(taskJsonRepresentation.getTaskType()))
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);

        executorService.executeTask(taskJsonRepresentation);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
