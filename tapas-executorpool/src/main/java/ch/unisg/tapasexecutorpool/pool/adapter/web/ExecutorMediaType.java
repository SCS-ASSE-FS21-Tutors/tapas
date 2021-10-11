package ch.unisg.tapasexecutorpool.pool.adapter.web;

import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import ch.unisg.tapasexecutorpool.pool.domain.ExecutorPool;
import org.json.JSONObject;

final public class ExecutorMediaType {
    public static final String EXECUTOR_MEDIA_TYPE = "application/json";

    public static String serialize(Executor executor) {
        JSONObject payload = new JSONObject();

        payload.put("executorId", executor.getExecutorId().getValue());
        payload.put("executorName", executor.getExecutorName().getValue());
        payload.put("executorType", executor.getExecutorType().getValue());
        payload.put("executorState", executor.getExecutorState().getValue());
        payload.put("executorPoolName", ExecutorPool.getTapasExecutorPool().getExecutorPoolName().getValue());

        return payload.toString();
    }

    private ExecutorMediaType() { }
}