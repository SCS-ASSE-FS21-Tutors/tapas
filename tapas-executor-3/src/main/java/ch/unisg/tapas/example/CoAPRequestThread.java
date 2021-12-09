package ch.unisg.tapas.example;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.io.TDGraphReader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.coap.Response;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
public class CoAPRequestThread extends Thread {

    // Temporary hardcoded address with thing description of miro card
    private final String miroCardTdUri = "http://yggdrasil.interactions.ics.unisg.ch/environments/61/workspaces/102/artifacts/mirogate";
    private final String readPropertyDefinitionUri = "https://www.w3.org/2019/wot/td#readProperty";

    private HttpClient client;
    private ObjectMapper objectMapper;
    private String taskId;
    private String taskInput;
    private String sparqlSearchEngineUri;
    private String executorPoolUri;


    public CoAPRequestThread(String taskId, String taskInput, String sparqlSearchEngineUri, String executorPoolUri) {
        this.client = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.taskId = taskId;
        this.taskInput = taskInput;
        this.sparqlSearchEngineUri = sparqlSearchEngineUri;
        this.executorPoolUri = executorPoolUri;
    }

    @Override
    public void run() {

        // Get the endpoints for the operations from the SparQL Search engine
        Map<String, String> endpointsMap = getEndpointsFromSearchEngine();

        // Check if operation is valid and send to endpoint
        if(endpointsMap.keySet().contains(taskInput)){
            String endpoint = endpointsMap.get(taskInput);
            log.info("Sending request to CoAP Server: " + endpoint);
            Request request = new Request(CoAP.Code.GET);
            request.setURI(endpoint);
            request.send();
            try {
                // Wait for response from the CoAP Server and parse actual result from response
                Response response = request.waitForResponse();
                String result = objectMapper.readValue(response.getPayloadString(), JsonNode.class).get("value").toString();
                log.info("CoAP Response received: " + result);

                // Send result back to executor pool
                String url = executorPoolUri + "completion";
                log.info("Sending result to Executor pool at: " + url);
                String inputDataJson = new JSONObject()
                        .put("taskId", taskId)
                        .put("outputData", result)
                        .toString();

                HttpRequest executorPoolRequest = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .headers("Content-Type", "application/json")
                        .PUT(HttpRequest.BodyPublishers.ofString(inputDataJson))
                        .build();
                HttpResponse executorPoolResponse = client.send(executorPoolRequest, HttpResponse.BodyHandlers.ofString());
                if (executorPoolResponse.statusCode() != 200) {
                    throw new RuntimeException("Executor pool responded with unexpected status code " +
                            executorPoolResponse.statusCode() + " instead of 200");
                }

            } catch (Exception e) {
                log.error("Processing CoAP Request failed");
                e.printStackTrace();
            }

        } else {
            throw new RuntimeException("Input data was not identified as possible operation in TD");
        }

    }

    private Map<String, String> getEndpointsFromSearchEngine(){
        log.info("Sending search query to search engine: "+ sparqlSearchEngineUri);

        /* TODO Implement querying of search engine to retrieve Miro Card TD
        String sparqlQuery ="@prefix td: <https://www.w3.org/2019/wot/td#>.\n" +
                "select ?x\n" +
                "where { ?x a td:Thing }";

        HttpRequest sparqlRequest = HttpRequest.newBuilder()
                .uri(URI.create(sparqlSearchEngineUri))
                .headers("Content-Type", "application/sparql-query")
                .POST(HttpRequest.BodyPublishers.ofString(sparqlQuery))
                .build();
        try {
            HttpResponse sparqlResponse = client.send(sparqlRequest, HttpResponse.BodyHandlers.ofString());
            log.info(sparqlResponse.body());
        } catch (Exception e ){
            e.printStackTrace();
        }
        */

        // Retrieve the endpoints for all defined operations and store them in HashMap
        log.info("Retrieving endpoints from things description of miro card: "+ miroCardTdUri);
        Map<String, String> endpointsMap = new HashMap<>();
        try {
            ThingDescription td = TDGraphReader.readFromURL(ThingDescription.TDFormat.RDF_TURTLE, miroCardTdUri);
            for(int i = 0; i< td.getProperties().size();i++){
                PropertyAffordance property = td.getProperties().get(i);
                if (CoAPExecutorController.POSSIBLE_INPUT.contains(property.getName())) {
                    Optional<Form> form = property.getFirstFormForOperationType(readPropertyDefinitionUri);
                    if (form.isPresent()) {
                        String target = form.get().getTarget();
                        endpointsMap.put(property.getName(), target);
                    }
                }
            }
        } catch (Exception e){
            log.error("Retrieving information from Miro TD failed");
            e.printStackTrace();
        }
        log.info("Endpoints found: "+ endpointsMap);

        return endpointsMap;
    }
}
