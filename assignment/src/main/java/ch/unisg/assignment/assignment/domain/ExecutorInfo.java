package ch.unisg.assignment.assignment.domain;

import lombok.Getter;
import lombok.Setter;

public class ExecutorInfo {
    @Getter
    @Setter
    private String ip;

    @Getter
    @Setter
    private int port;

    @Getter
    @Setter
    private String executorType;
}
