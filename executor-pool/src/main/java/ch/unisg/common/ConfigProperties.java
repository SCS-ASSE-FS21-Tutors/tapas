package ch.unisg.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class ConfigProperties {
    @Autowired
    private Environment environment;

    /**
     * Retrieves the URI of the WebSub hub. In this project, we use a single WebSub hub, but we could
     * use multiple.
     *
     * @return the URI of the WebSub hub
     */
    public URI getMqttBrokerUri() {
        return URI.create(environment.getProperty("mqtt.broker.uri"));
    }
}
