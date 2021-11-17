package ch.unisg.roster;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;

import ch.unisg.roster.roster.adapter.common.clients.TapasMqttClient;
import ch.unisg.roster.roster.adapter.in.messaging.mqtt.ExecutorEventMqttListener;
import ch.unisg.roster.roster.adapter.in.messaging.mqtt.ExecutorEventsMqttDispatcher;

@SpringBootApplication
public class RosterApplication {

    static Logger logger = Logger.getLogger(RosterApplication.class.getName());

    private static ConfigurableEnvironment ENVIRONMENT;

	public static void main(String[] args) {

		SpringApplication rosterApp = new SpringApplication(RosterApplication.class);
        ENVIRONMENT = rosterApp.run(args).getEnvironment();
        bootstrapMarketplaceWithMqtt();
	}

    /**
     * Connects to an MQTT broker, presumably the one used by all TAPAS groups to communicate with
     * one another
     */
    private static void bootstrapMarketplaceWithMqtt() {
        String broker = ENVIRONMENT.getProperty("mqtt.broker.uri");

        try {
            ExecutorEventsMqttDispatcher dispatcher = new ExecutorEventsMqttDispatcher();
            TapasMqttClient client = TapasMqttClient.getInstance(broker, dispatcher);
            client.startReceivingMessages();
        } catch (MqttException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

}
