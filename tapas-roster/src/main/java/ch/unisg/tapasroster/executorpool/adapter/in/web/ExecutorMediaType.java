package ch.unisg.tapasroster.executorpool.adapter.in.web;

import ch.unisg.tapasroster.executorpool.domain.Executor;
import ch.unisg.tapasroster.executorpool.domain.ExecutorPool;
import org.json.JSONObject;

final public class ExecutorMediaType {
    public static final String EXECUTOR_MEDIA_TYPE = "application/json";

    public static String serialize(Executor executor) {
        JSONObject payload = new JSONObject();

        payload.put("executorId", executor.getExecutorId().getValue());
        payload.put("executorName", executor.getExecutorName().getValue());
        payload.put("executorServer", executor.getExecutorServer().getValue());
        payload.put("executorPort", executor.getExecutorPort().getValue());
        payload.put("taskType", executor.getTaskType().getValue());
        payload.put("executorState", executor.getExecutorState().getValue());
        payload.put("executorPoolName", ExecutorPool.getTapasExecutorPool().getExecutorPoolName().getValue());

        return payload.toString();
    }

    private ExecutorMediaType() { }
}
