package ch.unisg.tapasexecutors.executors.domain;

import lombok.Getter;
import lombok.Value;
import org.springframework.stereotype.Component;

@Component
public interface Executors {

    public enum State {
        IDLE, BUSY
    }

    /* execution of executor specific task capability */
    public void startTask();

    /* completion of task results in event notification */
    public void completeTask();


    @Value
    public static class ExecutorId {
        private String value;
    }

    @Value
    public static class ExecutorName {
        private String value;
    }

    @Value
    public static class ExecutorState {
        private State value;
    }

    @Value
    public static class ExecutorType {
        private String value;
    }
}
