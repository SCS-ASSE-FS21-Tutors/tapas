package ch.unisg.tapasrobotexecutor.robotexecutor.adapter.in.web;

import ch.unisg.tapasrobotexecutor.robotexecutor.adapter.in.formats.TaskMediaType;
import ch.unisg.tapasrobotexecutor.robotexecutor.adapter.out.web.DeleteOperatorWebAdapter;
import ch.unisg.tapasrobotexecutor.robotexecutor.adapter.out.web.InitializeRobotPositionWebAdapter;
import ch.unisg.tapasrobotexecutor.robotexecutor.adapter.out.web.MoveBallOutOfSightWebAdapter;
import ch.unisg.tapasrobotexecutor.robotexecutor.adapter.out.web.RegisterNewOperatorWebAdapter;
import ch.unisg.tapasrobotexecutor.robotexecutor.domain.Task;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.TimeUnit;


@RestController
public class MoveRobotWebController {

    private static final Integer waitTimeBetweenRequests = 1;

    private static final Integer waitTimeToReachApogee = 20;

    private void waitSeconds(Integer secondAmount) {
        try {
            TimeUnit.SECONDS.sleep(secondAmount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(path = "/",consumes = TaskMediaType.TASK_MEDIA_TYPE)
    public ResponseEntity<String> move(@RequestBody TaskMediaType incomingTask) {
        JSONObject payload = new JSONObject();

        RegisterNewOperatorWebAdapter authorizeOperator = new RegisterNewOperatorWebAdapter();
        Optional<String> authKey = authorizeOperator.authorizeOperator();

        if (authKey.isPresent()) {
            String authKeyValue = authKey.get();
            payload.put("authKey", authKeyValue);

            waitSeconds(waitTimeBetweenRequests);

            MoveBallOutOfSightWebAdapter moveBallOutOfSightWebAdapter = new MoveBallOutOfSightWebAdapter();
            moveBallOutOfSightWebAdapter.moveBallOutOfSight(authKeyValue);

            waitSeconds(waitTimeToReachApogee);

            InitializeRobotPositionWebAdapter initializeRobotPositionWebAdapter = new InitializeRobotPositionWebAdapter();
            initializeRobotPositionWebAdapter.initializeRobotPosition(authKeyValue);

            waitSeconds(waitTimeBetweenRequests);

            DeleteOperatorWebAdapter deleteOperatorWebAdapter = new DeleteOperatorWebAdapter();
            deleteOperatorWebAdapter.deleteOperator(authKeyValue);

            incomingTask.setTaskStatus(Task.Status.EXECUTED.toString());
            incomingTask.setOutputData("");
            String jsonPayload = incomingTask.toJSON();
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, TaskMediaType.TASK_MEDIA_TYPE);
            return new ResponseEntity<>(jsonPayload,responseHeaders, HttpStatus.OK);
        } else {
            HttpHeaders responseHeaders = new HttpHeaders();
            incomingTask.setTaskStatus(Task.Status.FAILED.toString());
            incomingTask.setOutputData("");
            String jsonPayload = incomingTask.toJSON();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, TaskMediaType.TASK_MEDIA_TYPE);
            return new ResponseEntity<>(jsonPayload, responseHeaders, HttpStatus.CONFLICT);
        }
    }
}
