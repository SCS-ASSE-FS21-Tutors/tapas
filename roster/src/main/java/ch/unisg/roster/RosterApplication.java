package ch.unisg.roster;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ch.unisg.roster.roster.adapter.common.clients.TapasMqttClient;
import ch.unisg.roster.roster.adapter.in.messaging.mqtt.ExecutorEventMqttListener;
import ch.unisg.roster.roster.adapter.in.messaging.mqtt.ExecutorEventsMqttDispatcher;

@SpringBootApplication
public class RosterApplication {

    static Logger logger = Logger.getLogger(RosterApplication.class.getName());

    public static String MQTT_BROKER = "tcp://broker.hivemq.com:1883";

	public static void main(String[] args) {
		SpringApplication.run(RosterApplication.class, args);

        bootstrapMarketplaceWithMqtt();
	}

    /**
     * Connects to an MQTT broker, presumably the one used by all TAPAS groups to communicate with
     * one another
     */
    private static void bootstrapMarketplaceWithMqtt() {
        try {
            ExecutorEventsMqttDispatcher dispatcher = new ExecutorEventsMqttDispatcher();
            TapasMqttClient client = TapasMqttClient.getInstance(MQTT_BROKER, dispatcher);
            client.startReceivingMessages();
        } catch (MqttException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

}
