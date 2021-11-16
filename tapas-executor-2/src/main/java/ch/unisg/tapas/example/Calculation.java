package ch.unisg.tapas.example;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;


@Getter
@Setter
@RequiredArgsConstructor
public class Calculation {

    @Value( "${ch.unisg.tapas.executor-pool-url}" )
    private String executorPoolUri;

    @NonNull
    private String taskId;

    @NonNull
    private String inputData;

    public int execute(){
        int sum = 0;

        System.out.println(inputData);
        int values[] = {1,2,3};
        for(int i =0; i<values.length; i++){
            sum+= values[i];
        }

        try {
            TimeUnit.SECONDS.sleep(10);
            // Calls the /completion/ endpoint of the executor pool
            HttpClient client = HttpClient.newHttpClient();
            String url = executorPoolUri+ "completion/?taskId="+taskId;
            String inputDataJson = new JSONObject()
                    .put("taskId", taskId)
                    .put("outputData", sum)
                    .toString();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .headers("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(inputDataJson))
                    .build();

            java.net.http.HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        }catch (Exception e){
            System.out.println(e);
        }

        return sum;

    }

}
