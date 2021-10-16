package ch.unisg.executorpool.application.port.in;

import ch.unisg.executorpool.domain.ExecutorClass.ExecutorTaskType;
import ch.unisg.tapastasks.common.SelfValidating;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class GetAllExecutorInExecutorPoolByTypeQuery extends SelfValidating<GetAllExecutorInExecutorPoolByTypeQuery> {
    @NotNull
    private final ExecutorTaskType executorTaskType;

    public GetAllExecutorInExecutorPoolByTypeQuery(ExecutorTaskType executorTaskType){
        this.executorTaskType = executorTaskType;
        this.validateSelf();
    }
}
