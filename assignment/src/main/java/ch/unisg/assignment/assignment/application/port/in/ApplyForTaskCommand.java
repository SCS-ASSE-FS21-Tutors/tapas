package ch.unisg.assignment.assignment.application.port.in;

import javax.validation.constraints.NotNull;

import ch.unisg.assignment.assignment.domain.valueobject.ExecutorType;
import ch.unisg.assignment.assignment.domain.valueobject.IP4Adress;
import ch.unisg.assignment.assignment.domain.valueobject.Port;
import ch.unisg.assignment.common.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper=false)
public class ApplyForTaskCommand extends SelfValidating<ApplyForTaskCommand>{

    @NotNull
    private final ExecutorType taskType;

    @NotNull
    private final IP4Adress executorIP;


    @NotNull
    private final Port executorPort;

    public ApplyForTaskCommand(ExecutorType taskType, IP4Adress executorIP, Port executorPort) {
        this.taskType = taskType;
        this.executorIP = executorIP;
        this.executorPort = executorPort;
        this.validateSelf();
    }
}
