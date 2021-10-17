package ch.unisg.assignment.assignment.domain.valueobject;

import lombok.Value;

@Value
public class ExecutorType {
    private String value;

    public ExecutorType(String type) {
        this.value = type.toUpperCase();
    }
}
