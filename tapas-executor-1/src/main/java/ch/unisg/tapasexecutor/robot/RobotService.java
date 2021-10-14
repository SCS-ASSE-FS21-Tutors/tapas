package ch.unisg.tapasexecutor.robot;

import lombok.extern.java.Log;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

    public List<Robot> getRobots() {
        return List.of(
                new Robot(
                        400,
                        200,
                        0,
                        0
                )
        );
    }

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

}
