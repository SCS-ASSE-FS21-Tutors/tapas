package ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * Dispatches MQTT messages for known topics to associated event listeners. Used in conjunction with
 * {@link ch.unisg.tapas.auctionhouse.adapter.common.clients.TapasMqttClient}.
 *
 * This is where you would define MQTT topics and map them to event listeners (see
 * {@link AuctionEventsMqttDispatcher#initRouter()}).
 *
 * This class is only provided as an example to help you bootstrap the project. You are welcomed to
 * change this class as you see fit.
 */
@Component
public class AuctionEventsMqttDispatcher {
    private final Map<String, AuctionEventMqttListener> router;

    private AuctionAddedEventListenerMqttAdapter auctionAddedEventListenerMqttAdapter;
    private ExecutorAddedEventListenerMqttAdapter executorAddedEventListenerMqttAdapter;

    public AuctionEventsMqttDispatcher(@Autowired AuctionAddedEventListenerMqttAdapter auctionAddedEventListenerMqttAdapter, @Autowired ExecutorAddedEventListenerMqttAdapter executorAddedEventListenerMqttAdapter) {
        this.router = new Hashtable<>();
        this.auctionAddedEventListenerMqttAdapter = auctionAddedEventListenerMqttAdapter;
        this.executorAddedEventListenerMqttAdapter = executorAddedEventListenerMqttAdapter;
        this.initRouter();
    }

    private void initRouter() {
        router.put("ch/unisg/tapas/auctions", auctionAddedEventListenerMqttAdapter);
        router.put("ch/unisg/tapas-group-tutors/executors", executorAddedEventListenerMqttAdapter);
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
        AuctionEventMqttListener listener = router.get(topic);
        listener.handleEvent(message);
    }
}
