package ch.unisg.assignment.assignment.application.port.in;

import javax.validation.constraints.NotNull;

import ch.unisg.assignment.common.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper=false)
public class ApplyForTaskCommand extends SelfValidating<ApplyForTaskCommand>{

    @NotNull
    private final String taskType;

    @NotNull
    private final String executorIP;


    @NotNull
    private final int executorPort;

    public ApplyForTaskCommand(String taskType, String executorIP, int executorPort) {
        this.taskType = taskType;
        this.executorIP = executorIP;
        this.executorPort = executorPort;
        this.validateSelf();
    }
}
