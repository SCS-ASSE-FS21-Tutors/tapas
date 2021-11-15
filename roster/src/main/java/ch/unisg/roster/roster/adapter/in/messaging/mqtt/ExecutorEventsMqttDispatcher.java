package ch.unisg.roster.roster.adapter.in.messaging.mqtt;

import org.eclipse.paho.client.mqttv3.*;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class ExecutorEventsMqttDispatcher {
    private final Map<String, ExecutorEventMqttListener> router;

    public ExecutorEventsMqttDispatcher() {
        this.router = new Hashtable<>();
        initRouter();
    }

    // TODO: Register here your topics and event listener adapters
    private void initRouter() {
        router.put("ch/unisg/tapas/executors/added", new ExecutorAddedEventListenerMqttAdapter());
        router.put("ch/unisg/tapas/executors/removed", new ExecutorRemovedEventListenerMqttAdapter());
    }

    /**
     * Returns all topics registered with this dispatcher.
     *
     * @return the set of registered topics
     */
    public Set<String> getAllTopics() {
        return router.keySet();
    }

    /**
     * Dispatches an event received via MQTT for a given topic.
     *
     * @param topic the topic for which the MQTT message was received
     * @param message the received MQTT message
     */
    public void dispatchEvent(String topic, MqttMessage message) {
        ExecutorEventMqttListener listener = router.get(topic);
        listener.handleEvent(message);
    }
}
