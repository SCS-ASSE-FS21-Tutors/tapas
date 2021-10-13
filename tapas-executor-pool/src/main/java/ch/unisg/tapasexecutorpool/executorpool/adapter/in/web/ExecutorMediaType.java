package ch.unisg.tapasexecutorpool.executorpool.adapter.in.web;

import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import ch.unisg.tapasexecutorpool.executorpool.domain.ExecutorPool;

import org.json.JSONException;
import org.json.JSONObject;

final public class ExecutorMediaType {
    public static final String EXECUTOR_MEDIA_TYPE = "application/json";

    public static JSONObject toJSON(Executor executor) throws JSONException {
        JSONObject payload = new JSONObject();

        payload.put("executorId", executor.getExecutorId().getValue());
        payload.put("executorUrl", executor.getExecutorUrl().getValue());
        payload.put("executorType", executor.getExecutorType().getValue());
        payload.put("executorState", executor.getExecutorState().getValue());
        payload.put("executorPoolName", ExecutorPool.getTapasExecutorPool().getExecutorPoolName().getValue());

        return payload;
    }

    public static String serialize(Executor executor) throws JSONException {
        return toJSON(executor).toString();
    }

    private ExecutorMediaType() { }
}
