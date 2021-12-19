package ch.unisg.tapas.tapasexecutor2.web;

import ch.unisg.tapas.tapasexecutor2.application.ExecutorService;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class ExecutorWebController {

    @Autowired
    ExecutorService executorService;

    @PostMapping(path = "/execute", consumes = TaskJsonRepresentation.MEDIA_TYPE)
    public ResponseEntity executeTask(@RequestBody TaskJsonRepresentation taskJsonRepresentation) {
        log.info(
                "Received task {} of type {} with input data {} to execute",
                taskJsonRepresentation.getTaskId(),
                taskJsonRepresentation.getTaskType(),
                taskJsonRepresentation.getInputData());

        if (!"COMPUTATION".equals(taskJsonRepresentation.getTaskType()) && !"COMPUTATION_DEMO".equals(taskJsonRepresentation.getTaskType()))
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);

        executorService.executeTask(taskJsonRepresentation);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
