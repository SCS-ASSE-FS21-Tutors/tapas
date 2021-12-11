package ch.unisg.tapas.example.application;

import ch.unisg.ics.interactions.wot.td.ThingDescription;
import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.affordances.PropertyAffordance;
import ch.unisg.ics.interactions.wot.td.clients.TDCoapRequest;
import ch.unisg.ics.interactions.wot.td.clients.TDCoapResponse;
import ch.unisg.ics.interactions.wot.td.io.TDGraphReader;
import ch.unisg.ics.interactions.wot.td.vocabularies.TD;
import ch.unisg.tapas.example.formats.TaskJsonRepresentation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Log4j2
public class CoAPRequestThread extends Thread {

    // Temporary hardcoded address with thing description of miro card
    private final String miroCardTdUri = "http://yggdrasil.interactions.ics.unisg.ch/environments/61/workspaces/102/artifacts/mirogate";

    private HttpClient client;
    private ObjectMapper objectMapper;
    private TaskJsonRepresentation task;
    private String sparqlSearchEngineUri;
    private String executorPoolUri;


    public CoAPRequestThread(TaskJsonRepresentation task, String sparqlSearchEngineUri, String executorPoolUri) {
        this.client = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.task = task;
        this.sparqlSearchEngineUri = sparqlSearchEngineUri;
        this.executorPoolUri = executorPoolUri;
    }

    @Override
    public void run() {

        // Get the form for the relevant operation from the SparQL Search engine and TD
        Optional<Form> formOptional = getFormFromSearchEngine();

        // Execute CoAP request
        if (formOptional.isPresent()) {
            Form form = formOptional.get();
            log.info("Sending request to CoAP Server: " + form.getTarget());
            TDCoapRequest request = new TDCoapRequest(form, TD.readProperty);
            try {
                // Wait for response from the CoAP Server and parse actual result from response
                TDCoapResponse response = request.execute();
                if (response.getPayload().isEmpty()) {
                    throw new RuntimeException("No payload received from CoAP request");
                }
                String result = objectMapper.readValue(response.getPayload().get(), JsonNode.class).get("value").toString();
                log.info("CoAP Response received: " + result);

                // Send result back to executor pool
                String url = executorPoolUri + "completion";
                log.info("Sending result to Executor pool at: " + url);
                task.setOutputData(result);
                String bodyString = objectMapper.writeValueAsString(task);
                HttpRequest executorPoolRequest = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .headers("Content-Type", TaskJsonRepresentation.MEDIA_TYPE)
                        .PUT(HttpRequest.BodyPublishers.ofString(bodyString))
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
            throw new RuntimeException("Input data was not identified as possible property in TD");
        }

    }

    private Optional<Form> getFormFromSearchEngine() {
        log.info("Sending search query to search engine: " + sparqlSearchEngineUri);

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
        log.info("Retrieving readProperty-form from things description of miro card: " + miroCardTdUri);
        Optional<Form> resultForm = Optional.empty();
        try {
            ThingDescription td = TDGraphReader.readFromURL(ThingDescription.TDFormat.RDF_TURTLE, miroCardTdUri);
            Optional<PropertyAffordance> property = td.getPropertyByName("humidity");
            if (property.isPresent()) {
                Optional<Form> form = property.get().getFirstFormForOperationType(TD.readProperty);
                resultForm = form;
            }
        } catch (Exception e) {
            log.error("Retrieving information from Miro TD failed");
            e.printStackTrace();
        }
        return resultForm;
    }
}
