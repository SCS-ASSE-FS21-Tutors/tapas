package ch.unisg.executorpool.adapter.out.messaging;

import ch.unisg.executorpool.adapter.common.clients.TapasMqttClient;
import ch.unisg.executorpool.adapter.common.formats.ExecutorJsonRepresentation;
import ch.unisg.executorpool.application.port.out.ExecutorRemovedEventPort;
import ch.unisg.executorpool.domain.ExecutorAddedEvent;
import ch.unisg.executorpool.domain.ExecutorRemovedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Primary
public class PublishExecutorRemovedEventAdapter  implements ExecutorRemovedEventPort {

    private static final Logger LOGGER = LogManager.getLogger(PublishExecutorAddedEventAdapter.class);

    // TODO Can't autowire. Find fix
    /*
    @Autowired
    private ConfigProperties config;
    */

    @Autowired
    private Environment environment;

    @Override
    public void publishExecutorRemovedEvent(ExecutorRemovedEvent event){
        try{
            var mqttClient = TapasMqttClient.getInstance(environment.getProperty("mqtt.broker.uri"));
            mqttClient.publishMessage("ch/unisg/tapas/executors/removed", ExecutorJsonRepresentation.serialize(event.getExecutorClass()));
        }
        catch (MqttException e){
            LOGGER.error(e.getMessage(), e);
        }
    }
}
