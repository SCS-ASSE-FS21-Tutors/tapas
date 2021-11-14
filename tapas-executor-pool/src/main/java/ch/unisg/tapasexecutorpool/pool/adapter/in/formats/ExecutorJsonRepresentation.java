package ch.unisg.tapasexecutorpool.pool.adapter.in.formats;

import ch.unisg.tapascommon.tasks.domain.Task;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import ch.unisg.tapasexecutorpool.pool.domain.ExecutorPool;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

public class ExecutorJsonRepresentation {
    public static final String EXECUTOR_MEDIA_TYPE = "application/json";

    @Getter
    @Setter
    private String executorId;

    @Getter
    private final String executorName;

    @Getter
    private final String executorType;

    @Getter
    private final String executorAddress;

    @Getter
    @Setter
    private String executorState;

    @Getter
    private final String executorPoolName = ExecutorPool.getTapasExecutorPool().getExecutorPoolName().getValue();

    public ExecutorJsonRepresentation(String executorName, String executorType, String executorAddress) {
        this.executorName = executorName;
        this.executorType = executorType;
        this.executorAddress = executorAddress;
    }

    public ExecutorJsonRepresentation(Executor executor) {
        this.executorId = executor.getExecutorId().getValue();
        this.executorName = executor.getExecutorName().getValue();
        this.executorType = executor.getExecutorType().getValue().name();
        this.executorAddress = executor.getExecutorAddress().getValue();
        this.executorState = executor.getExecutorState().getValue().name();
    }

    public static String serialize(Executor executor) throws JsonProcessingException {
        var representation = new ExecutorJsonRepresentation(executor);
        return representation.serialize();
    }

    public String serialize() throws JsonProcessingException {
        var mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(this);
    }

    public static Executor deserialize(ExecutorJsonRepresentation representation) {
        return new Executor(
                new Executor.ExecutorId(representation.executorId),
                new Executor.ExecutorName(representation.executorName),
                new Executor.ExecutorType(Task.Type.valueOf(representation.executorType)),
                new Executor.ExecutorAddress(representation.executorAddress),
                new Executor.ExecutorState(Executor.State.valueOf(representation.executorState))
        );
    }

    public Executor deserialize() {
        return deserialize(this);
    }
}
