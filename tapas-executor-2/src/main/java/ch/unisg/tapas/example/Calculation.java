package ch.unisg.tapas.example;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.concurrent.TimeUnit;


@Getter
@Setter
@RequiredArgsConstructor
public class Calculation {

    @NonNull
    private String taskID;

    @NonNull
    private int[] values;

    /*
    @NonNull
    private String operator;
    */

    public int execute(){
        int sum = 0;

        for(int i =0; i<values.length; i++){
            sum+= values[i];
        }
        try {
            TimeUnit.SECONDS.sleep(10);
            // Calls the /completion/ endpoint of the executor pool
            String url = "http://127.0.0.1:8082/completion/?taskId="+taskID;
            CloseableHttpClient httpclient = HttpClients.createDefault();

            // Executes request
            HttpPut putMethod = new HttpPut(url);
            HttpResponse rawResponse = httpclient.execute(putMethod);
            System.out.println(rawResponse);

        }catch (Exception e){
            System.out.println(e);
        }

        return sum;

    }

}
