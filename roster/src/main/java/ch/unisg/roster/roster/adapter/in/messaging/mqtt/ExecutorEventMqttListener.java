package ch.unisg.roster.roster.adapter.in.messaging.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Abstract MQTT listener for auction-related events
 */
public abstract class ExecutorEventMqttListener {

    public abstract boolean handleEvent(MqttMessage message);
}
