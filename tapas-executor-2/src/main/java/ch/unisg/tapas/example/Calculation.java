package ch.unisg.tapas.example;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Getter
@Setter
@RequiredArgsConstructor
public class Calculation {

    private String executorPoolUri = "http://tapas-executor-pool:8083/";

    @NonNull
    private String taskId;

    @NonNull
    private String inputData;

    public int execute(){
        int result = 0;
        System.out.println("Received Input Data: "+ inputData);

        try {

            List<String> operators = new ArrayList<>();
            List<String> operands = new ArrayList<>();

            String temp = null;

            for(int i=0; i<inputData.length(); i++){
                if(inputData.charAt(i) == '+'){
                    operands.add("+");
                    operators.add(temp);
                    temp = "";
                }else if(inputData.charAt(i) == '-'){
                    operands.add("-");
                    operators.add(temp);
                    temp = "";
                }else if(inputData.charAt(i) == '*'){
                    operands.add("*");
                    operators.add(temp);
                    temp = "";
                }else if(inputData.charAt(i) == '/'){
                    operands.add("/");
                    operators.add(temp);
                    temp = "";
                }else {
                    if(i==0){
                        temp = String.valueOf(inputData.charAt(i));
                    }else if(i==inputData.length()-1){
                        temp = temp + String.valueOf(inputData.charAt(i));
                        operators.add(temp);
                    }
                    else {
                        temp = temp + String.valueOf(inputData.charAt(i));
                    }
                }
            }

            int output = Integer.parseInt(operators.get(0));
            for(int i=0; i<operands.size(); i++){
                if(operands.get(i) == "+"){
                    output += Integer.parseInt(operators.get(i+1));
                }else if(operands.get(i) == "-"){
                    output -= Integer.parseInt(operators.get(i+1));
                }else if(operands.get(i) == "*"){
                    output *= Integer.parseInt(operators.get(i+1));
                }
            }
            result = output;

            TimeUnit.SECONDS.sleep(10);
            // Calls the /completion/ endpoint of the executor pool
            HttpClient client = HttpClient.newHttpClient();
            String url = executorPoolUri+ "completion";
            String inputDataJson = new JSONObject()
                    .put("taskId", taskId)
                    .put("outputData", String.valueOf(result))
                    .toString();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .headers("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(inputDataJson))
                    .build();

            java.net.http.HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
        }catch (Exception e){
            System.out.println(e);
        }

        return result;

    }

}
