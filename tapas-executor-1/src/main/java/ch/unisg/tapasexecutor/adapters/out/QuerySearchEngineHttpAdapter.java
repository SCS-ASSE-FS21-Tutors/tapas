package ch.unisg.tapasexecutor.adapters.out;

import ch.unisg.tapasexecutor.application.ports.out.QuerySearchEnginePort;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
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

@Component
@Log4j2
public class QuerySearchEngineHttpAdapter implements QuerySearchEnginePort {


    @Value("${sparql.search.engine.uri}")
    private String sparqlSearchEngineUri;

    private final String SPARQL_QUERY = "@prefix td: <https://www.w3.org/2019/wot/td#>.\n" +
            "select ?x\n" +
            "where { ?x a td:Thing }";
    private final String KEYWORD = "leubot";

    private HttpClient client = HttpClient.newHttpClient();

    @Override
    public Optional<String> querySearchEngine() {
        log.info("Sending query for " + KEYWORD + " to search engine at " + sparqlSearchEngineUri);
        Optional<String> robotUri = Optional.empty();
        HttpRequest sparqlRequest = HttpRequest.newBuilder()
                .uri(URI.create(sparqlSearchEngineUri))
                .headers("Content-Type", "application/sparql-query")
                .POST(HttpRequest.BodyPublishers.ofString(SPARQL_QUERY))
                .build();
        try {
            HttpResponse sparqlResponse = client.send(sparqlRequest, HttpResponse.BodyHandlers.ofString());
            // Read values from XML-response and search for Leubot URI
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
                if (retrievedUri.contains(KEYWORD)) {
                    robotUri = Optional.of(retrievedUri);
                    log.info("Search engine returned the following Leubot URI: "+ retrievedUri);
                    break;
                }
            }
        } catch (Exception e) {
            log.error("Querying search engine failed");
            e.printStackTrace();
        }
        return robotUri;
    }
}
