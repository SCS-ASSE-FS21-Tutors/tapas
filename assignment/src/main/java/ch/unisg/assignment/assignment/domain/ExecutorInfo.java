package ch.unisg.assignment.assignment.domain;

import ch.unisg.assignment.assignment.domain.valueobject.ExecutorType;
import ch.unisg.assignment.assignment.domain.valueobject.IP4Adress;
import ch.unisg.assignment.assignment.domain.valueobject.Port;
import lombok.Getter;
import lombok.Setter;

public class ExecutorInfo {
    @Getter
    @Setter
    private IP4Adress ip;

    @Getter
    @Setter
    private Port port;

    @Getter
    @Setter
    private ExecutorType executorType;
}
