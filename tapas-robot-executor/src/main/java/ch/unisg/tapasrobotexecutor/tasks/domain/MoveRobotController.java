package ch.unisg.tapasrobotexecutor.tasks.domain;

import ch.unisg.tapasrobotexecutor.tasks.adapter.out.web.DeleteOperatorWebAdapter;
import ch.unisg.tapasrobotexecutor.tasks.adapter.out.web.InitializeRobotPositionWebAdapter;
import ch.unisg.tapasrobotexecutor.tasks.adapter.out.web.MoveBallOutOfSightWebAdapter;
import ch.unisg.tapasrobotexecutor.tasks.adapter.out.web.RegisterNewOperatorWebAdapter;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.TimeUnit;


@RestController
public class MoveRobotController {

    private static final Integer waitTimeBetweenRequests = 1;

    private static final Integer waitTimeToReachApogee = 20;

    private void waitSeconds(Integer secondAmount) {
        try {
            TimeUnit.SECONDS.sleep(secondAmount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(path = "/executeTask/")
    public ResponseEntity<String> move() {
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

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
            return new ResponseEntity<>(payload.toString(), responseHeaders, HttpStatus.OK);
        } else {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
            return new ResponseEntity<>("", responseHeaders, HttpStatus.CONFLICT);
        }
    }
}
