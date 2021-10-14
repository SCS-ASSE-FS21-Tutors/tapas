package ch.unisg.tapasexecutors.executors.domain;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class RobotExecutorController {

    private final RobotExecutor robotExecutor;

    public RobotExecutorController(RobotExecutor robotExecutor) {
        this.robotExecutor = robotExecutor;
    }

    @PostMapping("/executors/robot/")
    public ResponseEntity<RobotExecutor> executeRobotExecutor(@RequestBody RobotExecutor robotExecutor) {
        if (robotExecutor == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        else {
            return new ResponseEntity<>(robotExecutor, HttpStatus.CREATED);
        }
    }
}
