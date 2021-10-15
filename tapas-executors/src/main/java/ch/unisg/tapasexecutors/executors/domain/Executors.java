package ch.unisg.tapasexecutors.executors.domain;

import lombok.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Interface for individual Executors with different execution approaches
 */
@Component
public interface Executors {

    enum State {
        IDLE, BUSY
    }

    // start of execution of executor specific task capability
    void startTask() throws IOException, InterruptedException;

    // completion of task results in event notification
    void completeTask();

    // execution of task type
    void execute() throws IOException, InterruptedException;

    @Value
    class ExecutorId {
        private String value;
    }

    @Value
    class ExecutorName {
        private String value;
    }

    @Value
    class ExecutorState {
        private State value;
    }

    @Value
    class ExecutorType {
        private String value;
    }
}
