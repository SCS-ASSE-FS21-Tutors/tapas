package ch.unisg.tapasexecutor.adapters.out.robot;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.ActionAffordance;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.clients.TDHttpRequest;
import ch.unisg.ics.interactions.wot.td.clients.TDHttpResponse;
import ch.unisg.ics.interactions.wot.td.io.TDGraphReader;
import ch.unisg.ics.interactions.wot.td.schemas.DataSchema;
import ch.unisg.ics.interactions.wot.td.schemas.ObjectSchema;
import ch.unisg.ics.interactions.wot.td.security.APIKeySecurityScheme;
import ch.unisg.ics.interactions.wot.td.security.SecurityScheme;
import ch.unisg.ics.interactions.wot.td.vocabularies.TD;
import ch.unisg.ics.interactions.wot.td.vocabularies.WoTSec;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
public class RobotApi implements Closeable {

    private static final long rateLimitingMillis = 1100;
    private ThingDescription td;
    private final String robotUri;
    private final HttpClient client = HttpClient.newHttpClient();
    private String apiKey;
    private String apiKeyDeleteEndpoint;
    private final ObjectMapper om = new ObjectMapper();
    private Instant lastAPICall = Instant.ofEpochSecond(0);

    public RobotApi(String robotTDUri) throws Exception {
        this.td = TDGraphReader.readFromURL(ThingDescription.TDFormat.RDF_TURTLE, robotTDUri);
        this.robotUri = td.getBaseURI().get();
    }

    public static RobotApi open(String robotTDUri) throws Exception {

        var api = new RobotApi(robotTDUri);
        api.createUser();
        return api;
    }

    private void createUser() throws Exception {

        var body = new HashMap();
        body.put("name", "TAPAS Group 3");
        body.put("email", "weDontHaveAEmailButYouCanOpenAIssueONGitHub@tapas.SCS-ASSE-FS21-Group3.github.com");

        // TODO: This request cannot be decoupled, because there is no option to retrieve headers from TDHttpResponse
        // https://github.com/Interactions-HSG/wot-td-java/issues/67
        var response = makeRequest("POST", "user", body);

        var location = response.headers().firstValue("Location");
        this.apiKeyDeleteEndpoint = location.get();
        var idx = apiKeyDeleteEndpoint.lastIndexOf("/");
        this.apiKey = apiKeyDeleteEndpoint.substring(idx + 1);
    }

    private TDHttpResponse makeRequestDecoupled(String actionName, Map<String, Object> payload) throws Exception {

        Optional<ActionAffordance> actionAffordance = td.getActionByName(actionName);
        if (actionAffordance.isEmpty())
            throw new RuntimeException("Action \"" + actionName + "\" not found in things description");

        Optional<Form> form = actionAffordance.get().getFirstFormForOperationType(TD.invokeAction);
        if (form.isEmpty())
            throw new RuntimeException("Form for action \"" + actionName + "\" not found in things description");

        TDHttpRequest request = new TDHttpRequest(form.get(), TD.invokeAction);

        // Check if the action requires a payload
        Optional<DataSchema> inputSchema = actionAffordance.get().getInputSchema();
        if (inputSchema.isPresent()) {
            request.setObjectPayload((ObjectSchema) inputSchema.get(), payload);
        }
        // Add api key if we have one
        if (apiKey != null) {
            Optional<SecurityScheme> securityScheme = td.getFirstSecuritySchemeByType(WoTSec.APIKeySecurityScheme);
            if (securityScheme.isPresent()) {
                request.setAPIKey((APIKeySecurityScheme) securityScheme.get(), apiKey);
            }
        }

        // Wait for rate limiting
        var timeSinceLastCall = Duration.between(lastAPICall, Instant.now()).toMillis();

        if (timeSinceLastCall < rateLimitingMillis)
            Thread.sleep(rateLimitingMillis - timeSinceLastCall);


        TDHttpResponse response = request.execute();
        lastAPICall = Instant.now();

        // Log response
        int statusCode = response.getStatusCode();
        log.info("TD HTTP Request action \"" + actionName + "\" resulted in status code " + statusCode);

        // Check if ok
        if (statusCode >= 300 || statusCode < 200)
            throw new RuntimeException("Unexpected status code: " + statusCode);

        return response;
    }

    private HttpResponse<String> makeRequest(String method, String endpoint, Map<String, Object> body) throws Exception {

        String bodyString = "";

        // Create http request builder
        var uri = endpoint.startsWith("http") ? URI.create(endpoint) : URI.create(this.robotUri + endpoint);
        var builder = HttpRequest.newBuilder().uri(uri);

        // Add body if we have one
        if (body == null) {
            builder = builder.method(method, HttpRequest.BodyPublishers.noBody());
        } else {
            bodyString = om.writeValueAsString(body);
            builder = builder.header("Content-Type", "application/json");
            builder = builder.method(method, HttpRequest.BodyPublishers.ofString(bodyString));
        }

        // Add api key if we have one
        if (apiKey != null)
            builder = builder.header("X-API-KEY", apiKey);

        // Wait for rate limiting
        var timeSinceLastCall = Duration.between(lastAPICall, Instant.now()).toMillis();

        if (timeSinceLastCall < rateLimitingMillis)
            Thread.sleep(rateLimitingMillis - timeSinceLastCall);

        // Build and send request
        var request = builder.build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        lastAPICall = Instant.now();

        // Log response
        int statusCode = response.statusCode();
        log.info("Http Request: " + method + " " + endpoint + " " + statusCode + " " + bodyString);

        // Check if ok
        if (statusCode >= 300 || statusCode < 200)
            throw new RuntimeException("Unexpected status code: " + statusCode);

        return response;
    }

    public void dance() throws Exception {

        moveRobot("setElbow", 400);
        moveRobot("setElbow", 650);
        moveRobot("setElbow", 400);
        moveRobot("setElbow", 650);
        moveRobot("reset", 0);
    }

    private void moveRobot(String actionName, int value) throws Exception {

        var body = new HashMap<String, Object>();
        body.put("value", value);

        makeRequestDecoupled(actionName,body);
    }

    @Override
    public void close() throws IOException {

        try {
            if (this.apiKeyDeleteEndpoint != null)
                // This request cannot be decoupled, because there is no option to retrieve headers from TDHttpResponse
                makeRequest("DELETE", this.apiKeyDeleteEndpoint, null);
        } catch (Exception exception) {
            throw new IOException(exception);
        }

    }

}
