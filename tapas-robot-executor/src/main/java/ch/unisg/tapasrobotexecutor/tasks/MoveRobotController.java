package ch.unisg.tapasrobotexecutor.tasks;

import ch.unisg.tapasrobotexecutor.tasks.adapter.out.web.DeleteOperatorWebAdapter;
import ch.unisg.tapasrobotexecutor.tasks.adapter.out.web.InitializeRobotPositionWebAdapter;
import ch.unisg.tapasrobotexecutor.tasks.adapter.out.web.MoveBallOutOfSightWebAdapter;
import ch.unisg.tapasrobotexecutor.tasks.adapter.out.web.RegisterNewOperatorWebAdapter;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @GetMapping(path = "/move")
    public ResponseEntity<String> move() {
        JSONObject payload = new JSONObject();

        Optional<String> authKey = RegisterNewOperatorWebAdapter.authorizeOperator();
        if (authKey.isPresent()){
            String authKeyValue = authKey.get();
            payload.put("authKey", authKeyValue);

            waitSeconds(waitTimeBetweenRequests);

            MoveBallOutOfSightWebAdapter.moveBall(authKeyValue);

            waitSeconds(waitTimeToReachApogee);

            InitializeRobotPositionWebAdapter.backToInitialPosition(authKeyValue);

            waitSeconds(waitTimeBetweenRequests);

            DeleteOperatorWebAdapter.deleteOperator(authKeyValue);

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
