package ch.unisg.tapasexecutormiro.executor.application.service;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.elements.exception.ConnectorException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@AllArgsConstructor
@Primary
@Component
public class ExecuteMiroTask {

    private static final Logger LOGGER = LogManager.getLogger(ExecuteMiroTask.class);

    @GetMapping(path = "/miro/")
    public void retrieveTaskFromTaskList() {

        CoapClient client = new CoapClient("coap://130.82.171.10:5683/humidity");

        String result = null;
        CoapResponse response = null;
        try {
            result = client.get().getResponseText();
            client.post("payload", MediaTypeRegistry.TEXT_PLAIN);
        } catch (ConnectorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.info("Response: " + result);
        LOGGER.info("Response CODE: " + response.getCode());

    }
}
