package ch.unisg.tapasexecutorpool.common.formats;

import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import org.json.JSONObject;

final public class ExecutorJsonRepresentation {
    public static final String MEDIA_TYPE = "application/json";

    public static String serialize(Executor executor) {
        JSONObject payload = new JSONObject();

        payload.put("executorId", executor.getExecutorId().getValue());
        payload.put("executorName", executor.getExecutorName().getValue());
        payload.put("executorType", executor.getExecutorType().getValue());
        payload.put("executorState", executor.getExecutorState().getValue());
        payload.put("executorUrl", executor.getExecutorUrl().getValue());

        return payload.toString();
    }

    private ExecutorJsonRepresentation() { }
}
