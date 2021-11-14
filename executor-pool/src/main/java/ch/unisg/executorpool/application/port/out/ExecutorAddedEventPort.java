package ch.unisg.executorpool.application.port.out;

import ch.unisg.executorpool.domain.ExecutorAddedEvent;
import org.eclipse.paho.client.mqttv3.MqttException;

public interface ExecutorAddedEventPort {
    void publishExecutorAddedEvent(ExecutorAddedEvent event);
}
