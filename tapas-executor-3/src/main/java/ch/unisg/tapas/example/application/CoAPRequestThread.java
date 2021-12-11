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
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Log4j2
public class CoAPRequestThread extends Thread {

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

        Optional<String> miroCardUri = getMiroCardUriFromSearchEngine();
        if (miroCardUri.isPresent()) {
            // Get the form for the relevant operation from the SparQL Search engine and TD
            Optional<Form> formOptional = getFormFromMiroCardUri(miroCardUri.get());
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
                    } else {
                        log.info("Result received by executor pool");
                    }

                } catch (Exception e) {
                    log.error("Processing CoAP Request failed");
                    e.printStackTrace();
                }

            } else {
                throw new RuntimeException("No humidity property could be found on the Miro Card TD");
            }

        } else throw new RuntimeException("No Miro card URI could be retrieved from Search Engine");

    }

    private Optional<String> getMiroCardUriFromSearchEngine() {
        log.info("Sending search query to search engine: " + sparqlSearchEngineUri);

        // Querying of search engine to retrieve Miro Card TD URI
        Optional<String> miroCardUri = Optional.empty();

        String sparqlQuery = "@prefix td: <https://www.w3.org/2019/wot/td#>.\n" +
                "select ?x\n" +
                "where { ?x a td:Thing }";

        HttpRequest sparqlRequest = HttpRequest.newBuilder()
                .uri(URI.create(sparqlSearchEngineUri))
                .headers("Content-Type", "application/sparql-query")
                .POST(HttpRequest.BodyPublishers.ofString(sparqlQuery))
                .build();

        try {
            HttpResponse sparqlResponse = client.send(sparqlRequest, HttpResponse.BodyHandlers.ofString());
            // Read values from XML-response and search for mirogate URI
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            StringBuilder xmlStringBuilder = new StringBuilder();

            String xmlString = sparqlResponse.body().toString().replace("\n", "");
            xmlStringBuilder.append(xmlString);
            ByteArrayInputStream input = new ByteArrayInputStream(
                    xmlStringBuilder.toString().getBytes(StandardCharsets.UTF_8));
            Document doc = builder.parse(input);
            doc.getDocumentElement().normalize();
            NodeList resultElements = doc.getElementsByTagName("result");
            for (int i = 0; i < resultElements.getLength(); i++) {
                String retrievedUri = resultElements.item(i).getFirstChild().getFirstChild().getTextContent();
                if (retrievedUri.contains("mirogate")) {
                    miroCardUri = Optional.of(retrievedUri);
                    break;
                }
            }
        } catch (Exception e) {
            log.error("Querying search engine failed");
            e.printStackTrace();
        }
        return miroCardUri;
    }

    public Optional<Form> getFormFromMiroCardUri(String miroCardUri) {
        // Retrieve the possible properties of the TD and return the one for humidity
        log.info("Retrieving readProperty-form from things description of miro card: " + miroCardUri);
        Optional<Form> resultForm = Optional.empty();
        try {
            ThingDescription td = TDGraphReader.readFromURL(ThingDescription.TDFormat.RDF_TURTLE, miroCardUri);
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
