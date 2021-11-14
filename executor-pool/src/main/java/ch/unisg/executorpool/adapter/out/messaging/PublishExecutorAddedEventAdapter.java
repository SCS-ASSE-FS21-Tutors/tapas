package ch.unisg.executorpool.adapter.out.messaging;

import ch.unisg.common.ConfigProperties;
import ch.unisg.executorpool.adapter.common.clients.TapasMqttClient;
import ch.unisg.executorpool.adapter.common.formats.ExecutorJsonRepresentation;
import ch.unisg.executorpool.application.port.out.ExecutorAddedEventPort;
import ch.unisg.executorpool.domain.ExecutorAddedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@Primary
public class PublishExecutorAddedEventAdapter implements ExecutorAddedEventPort {

    private static final Logger LOGGER = LogManager.getLogger(PublishExecutorAddedEventAdapter.class);

    // TODO Can't autowire. Find fix
    /*
    @Autowired
    private ConfigProperties config;
    */

    @Autowired
    private Environment environment;

    @Override
    public void publishExecutorAddedEvent(ExecutorAddedEvent event){
        try{
            var mqttClient = TapasMqttClient.getInstance(environment.getProperty("mqtt.broker.uri"));
            mqttClient.publishMessage("ch/unisg/tapas/executors/added", ExecutorJsonRepresentation.serialize(event.getExecutorClass()));
        }
        catch (MqttException e){
            LOGGER.error(e.getMessage(), e);
        }
    }
}
