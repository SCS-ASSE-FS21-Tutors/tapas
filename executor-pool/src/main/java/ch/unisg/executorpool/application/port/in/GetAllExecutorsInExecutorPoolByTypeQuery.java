package ch.unisg.executorpool.application.port.in;

import ch.unisg.executorpool.domain.ExecutorClass.ExecutorTaskType;
import ch.unisg.common.SelfValidating;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class GetAllExecutorsInExecutorPoolByTypeQuery extends SelfValidating<GetAllExecutorsInExecutorPoolByTypeQuery> {
    @NotNull
    private final ExecutorTaskType executorTaskType;

    public GetAllExecutorsInExecutorPoolByTypeQuery(ExecutorTaskType executorTaskType){
        this.executorTaskType = executorTaskType;
        this.validateSelf();
    }
}
