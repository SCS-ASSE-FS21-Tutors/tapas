package ch.unisg.tapasexecutor.adapters.in;

import ch.unisg.tapasexecutor.application.ports.in.ExecuteRobotTaskUseCase;
import ch.unisg.tapasexecutor.application.ports.in.IsTaskAcceptableQuery;
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
    ExecuteRobotTaskUseCase executeRobotTaskUseCase;

    @Autowired
    IsTaskAcceptableQuery isTaskAcceptableQuery;

    @PostMapping(path = "/execute", consumes = TaskJsonRepresentation.MEDIA_TYPE)
    public ResponseEntity executeTask(@RequestBody TaskJsonRepresentation taskJsonRepresentation) {
        log.info(
                "Received task {} of type {} to execute",
                taskJsonRepresentation.getTaskId(),
                taskJsonRepresentation.getTaskType());

        var task = taskJsonRepresentation.toTask();

        if (!isTaskAcceptableQuery.isAcceptable(task))
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);

        executeRobotTaskUseCase.executeRobotTask(task);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
