package ch.unisg.tapasexecutor.web;

import ch.unisg.tapasexecutor.service.RobotService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
public class ExecutorController {

    @Autowired
    RobotService robotService;

    @PostMapping("/execute")
    public ResponseEntity executeTask(@RequestBody TaskJsonRepresentation taskJsonRepresentation){

        var task = taskJsonRepresentation.toTask();

        if(! robotService.isAcceptable(task))
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);

        robotService.executeRobotTaskAsynchronously(task);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
