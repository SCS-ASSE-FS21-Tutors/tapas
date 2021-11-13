package ch.unisg.tapasexecutor.robot;

import lombok.extern.java.Log;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.policy.TimeoutRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log
@Service
public class RobotService {

    public ResponseEntity<?> getUser(){

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        Map map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");

        headers.setAll(map);

        HttpEntity<?> request = new HttpEntity<>(headers);
        String url = "https://api.interactions.ics.unisg.ch/leubot1/v1.3.0/user";

        ResponseEntity<?> response = new RestTemplate().getForEntity(url, String.class);
        log.info("Returned User");
        return response;
    }

    public Optional<String> addUser(){

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        Map map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");

        headers.setAll(map);

        Map req_payload = new HashMap();
        req_payload.put("name", "test");
        req_payload.put("email", "test@unisg.ch");

        HttpEntity<?> request = new HttpEntity<>(req_payload, headers);
        String url = "https://api.interactions.ics.unisg.ch/leubot1/v1.3.0/user";

        ResponseEntity<String> response = new RestTemplate().postForEntity(url, request, String.class);
        JSONObject jsonResponse = new JSONObject(response);
        String message = jsonResponse.getJSONObject("headers").getJSONArray("Location").toString();
        String apikey = message.substring(60,92);
        Optional<String> restCall = Optional.of(apikey);
        log.info(apikey);
        return restCall;
    }

    public void deleteUser(String apiKey){
        Map map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = "https://api.interactions.ics.unisg.ch/leubot1/v1.3.0/user" + "/" + apiKey;

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.delete(url, map);
        log.info("User deleted successfully");
    }

    public void moveElbow(int value, String apiKey){

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        Map map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        map.put("X-API-KEY", apiKey);
        String url = "https://api.interactions.ics.unisg.ch/leubot1/v1.3.0/elbow";

        headers.setAll(map);

        Map req_payload = new HashMap();
        req_payload.put("value", value);


        HttpEntity<?> request = new HttpEntity<>(req_payload, headers);

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.put(url, request, String.class);
        log.info("Elbow moved");
    }

    public void moveWristAngle(int value, String apiKey){

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        Map map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        map.put("X-API-KEY", apiKey);
        String url = "https://api.interactions.ics.unisg.ch/leubot1/v1.3.0/wrist/angle";

        headers.setAll(map);

        Map req_payload = new HashMap();
        req_payload.put("value", value);


        HttpEntity<?> request = new HttpEntity<>(req_payload, headers);

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.put(url, request, String.class);
        log.info("Wrist angle moved");
    }

    public void moveWristRotation(int value, String apiKey){

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        Map map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        map.put("X-API-KEY", apiKey);
        String url = "https://api.interactions.ics.unisg.ch/leubot1/v1.3.0/wrist/rotation";

        headers.setAll(map);

        Map req_payload = new HashMap();
        req_payload.put("value", value);


        HttpEntity<?> request = new HttpEntity<>(req_payload, headers);

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.put(url, request, String.class);
        log.info("Wrist rotation moved");
    }

    public void moveGripper(int value, String apiKey){

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        Map map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        map.put("X-API-KEY", apiKey);
        String url = "https://api.interactions.ics.unisg.ch/leubot1/v1.3.0/gripper";

        headers.setAll(map);

        Map req_payload = new HashMap();
        req_payload.put("value", value);


        HttpEntity<?> request = new HttpEntity<>(req_payload, headers);

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.put(url, request, String.class);
        log.info("Gripper moved");
    }

    public void moveReset(int value, String apiKey){

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        Map map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        map.put("X-API-KEY", apiKey);
        String url = "https://api.interactions.ics.unisg.ch/leubot1/v1.3.0/reset";

        headers.setAll(map);

        Map req_payload = new HashMap();
        req_payload.put("value", value);


        HttpEntity<?> request = new HttpEntity<>(req_payload, headers);

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.put(url, request, String.class);
        log.info("Robot reset");
    }

    public void moveRobot(String apiKeyValue) {
        waitAndSleep();

        moveElbow(450, apiKeyValue);

        waitAndSleep();

        moveWristAngle(550, apiKeyValue);

        waitAndSleep();

        moveWristRotation(550, apiKeyValue);

        waitAndSleep();

        moveGripper(200, apiKeyValue);

        waitAndSleep();

        moveReset(0, apiKeyValue);

        waitAndSleep();

    }

    public void waitAndSleep() {
        try
        {
            Thread.sleep(8000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

}
