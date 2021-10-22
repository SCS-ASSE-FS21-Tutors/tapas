package ch.unisg.tapasexecutorcalc.executor.domain;

import lombok.Getter;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class ExecutorStateChangedEvent {
    @Getter
    @NotNull
    String id;

    @Getter
    @NotNull
    String state;

    public ExecutorStateChangedEvent(String id, String state) {
        this.id = id;
        this.state = state;
    }
}
