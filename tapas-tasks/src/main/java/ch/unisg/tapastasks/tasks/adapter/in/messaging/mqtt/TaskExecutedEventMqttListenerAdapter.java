package ch.unisg.tapastasks.tasks.adapter.in.messaging.mqtt;

import ch.unisg.tapastasks.tasks.application.handler.TaskExecutedHandler;
import ch.unisg.tapastasks.tasks.application.port.in.TaskExecutedEvent;
import ch.unisg.tapastasks.tasks.application.port.in.TaskExecutedEventHandler;
import ch.unisg.tapastasks.tasks.domain.Task.*;
import org.eclipse.paho.client.mqttv3.*;

import java.util.UUID;

public class TaskExecutedEventMqttListenerAdapter {

    String topic = "tapas/tasks/executed";
    String broker = "tcp://127.0.0.1:1883";
    String clientId = UUID.randomUUID().toString();

    public TaskExecutedEventMqttListenerAdapter() {
        this.startReceivingNewTasks();
    }

    public void startReceivingNewTasks() {
        try {
            MqttClient tapasTasksReceiverClient = new MqttClient(broker,clientId);
            tapasTasksReceiverClient.connect();
            tapasTasksReceiverClient.subscribe(topic);
            tapasTasksReceiverClient.setCallback(new TaskExecutedReceivedCallback());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private class TaskExecutedReceivedCallback implements MqttCallback {

        @Override
        public void connectionLost(Throwable cause) {

        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            String taskId = new String(message.getPayload());
            System.out.println("New message received: " + taskId);
            TaskExecutedEvent taskExecutedEvent = new TaskExecutedEvent(new TaskId(taskId));
            TaskExecutedEventHandler taskExecutedEventHandler = new TaskExecutedHandler();
            taskExecutedEventHandler.handleTaskExecuted(taskExecutedEvent);
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {

        }
    }
}
