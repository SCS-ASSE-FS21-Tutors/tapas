package ch.unisg.tapasadditionexecutor;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;


@RestController
public class AddNumbersController {

    @GetMapping(path = "/addition")
    public ResponseEntity<String> addNumbers() {
        Random rand = new Random(123);
        float operator1 = rand.nextFloat();
        float operator2 = rand.nextFloat();

        JSONObject payload = new JSONObject();

        payload.put("operator1", operator1);
        payload.put("operator2", operator2);
        payload.put("result", operator1+operator2);

        // Add the content type as a response header
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return new ResponseEntity<>(payload.toString(), responseHeaders, HttpStatus.OK);
    }
}
