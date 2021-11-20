package ch.unisg.tapastasks.tasks.adapter.out.web;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import ch.unisg.tapastasks.tasks.adapter.in.formats.TaskJsonPatchRepresentation;
import ch.unisg.tapastasks.tasks.application.port.out.ExternalTaskExecutedEvent;
import ch.unisg.tapastasks.tasks.application.port.out.ExternalTaskExecutedEventHandler;

@Component
@Primary
public class ExternalTaskExecutedWebAdapter implements ExternalTaskExecutedEventHandler {

    Logger logger = Logger.getLogger(ExternalTaskExecutedWebAdapter.class.getName());

    /**
    *   Updates an external task which got executed in our system.
    **/
    @Override
    public void handleEvent(ExternalTaskExecutedEvent externalTaskExecutedEvent) {

        JSONObject op1;
        JSONObject op2;
        try {
            op1 = new JSONObject()
            .put("op", "replace")
            .put("path", "/taskStatus")
            .put("value", "EXECUTED");

            op2 = new JSONObject()
            .put("op", "add")
            .put("path", "/outputData")
            .put("value", "0");
        } catch (JSONException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
            return;
        }

        String body = new JSONArray().put(op1).put(op2).toString();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(externalTaskExecutedEvent.getOriginalTaskUri().getValue()))
            .header("Content-Type", TaskJsonPatchRepresentation.MEDIA_TYPE)
            .method("PATCH", HttpRequest.BodyPublishers.ofString(body))
            .build();


        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }

    }

}
