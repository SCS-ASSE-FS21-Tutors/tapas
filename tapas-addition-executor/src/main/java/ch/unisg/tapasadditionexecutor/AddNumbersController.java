package ch.unisg.tapasadditionexecutor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class AddNumbersController {
    @GetMapping(path = "/", consumes = {TaskMediaType.TASK_MEDIA_TYPE})
    public ResponseEntity<String> addNumbers(@RequestBody TaskMediaType payload) {
        String inputData = payload.getInputData();
        int plusPosition = inputData.indexOf("+");
        int minusPosition = inputData.indexOf("-");
        int timesPosition = inputData.indexOf("*");
        //Check if there is more than one operator
        String operator = "";
        HttpStatus returnStatus;

        try {
            if (plusPosition * minusPosition * timesPosition < 0) {
                System.out.println("Just one operator allowed");
                payload.setTaskStatus("FAILED");
            } else {
                if (plusPosition > 0) {
                    operator = "\\+";
                } else if (minusPosition > 0) {
                    operator = "-";
                } else if (timesPosition > 0) {
                    operator = "\\*";
                }
            }

            int operand1 = Integer.parseInt(inputData.split(operator)[0]);
            int operand2 = Integer.parseInt(inputData.split(operator)[1]);
            int computationResult = 0;
            switch (operator) {
                case "\\+":
                    computationResult = operand1 + operand2;
                    payload.setTaskStatus("EXECUTED");
                    returnStatus = HttpStatus.OK;
                    break;
                case "\\*":
                    computationResult = operand1 * operand2;
                    payload.setTaskStatus("EXECUTED");
                    returnStatus = HttpStatus.OK;
                    break;
                case "-":
                    computationResult = operand1 - operand2;
                    payload.setTaskStatus("EXECUTED");
                    returnStatus = HttpStatus.OK;
                    break;
                default:
                    returnStatus = HttpStatus.BAD_REQUEST;
                    payload.setTaskStatus("FAILED");
                    break;
            }
            payload.setOutputData(String.valueOf(computationResult));
        } catch (Exception exx) {
            payload.setTaskStatus("FAILED");
            returnStatus = HttpStatus.BAD_REQUEST;
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/task+json");

        String jsonResponse = payload.toJSON();
        return new ResponseEntity<>(jsonResponse, responseHeaders, returnStatus);
    }
}
