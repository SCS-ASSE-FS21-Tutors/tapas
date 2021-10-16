package ch.unisg.tapasexecutor.robot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Log
@RestController
@RequestMapping("/robot")
public class RobotController {

    private final RobotService robotService;

    @Autowired
    public RobotController(RobotService robotService) {
        this.robotService = robotService;
    }


    @RequestMapping("/getUser")
    public ResponseEntity<?> getUser() {
        return robotService.getUser();
    }

    @RequestMapping("/addUser")
    public Optional<String> addUser() {
        return robotService.addUser();
    }



    @RequestMapping("/moveRobot")
    public void setupRobot() {
        Optional<String> apiKey = robotService.addUser();

        if (apiKey.isPresent()) {
            String apiKeyValue = apiKey.get();

            System.out.println(apiKeyValue);

            waitAndSleep();

            robotService.moveElbow(450, apiKeyValue);

            waitAndSleep();

            robotService.moveWristAngle(550, apiKeyValue);

            waitAndSleep();

            robotService.moveWristRotation(550, apiKeyValue);

            waitAndSleep();

            robotService.moveGripper(200, apiKeyValue);

            waitAndSleep();

            robotService.moveReset(0, apiKeyValue);

            waitAndSleep();

            robotService.deleteUser(apiKeyValue);

        }
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


