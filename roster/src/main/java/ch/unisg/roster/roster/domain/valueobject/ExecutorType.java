package ch.unisg.roster.roster.domain.valueobject;

import lombok.Value;

@Value
public class ExecutorType {
    private String value;

    public ExecutorType(String type) {
        this.value = type.toUpperCase();
    }
}
