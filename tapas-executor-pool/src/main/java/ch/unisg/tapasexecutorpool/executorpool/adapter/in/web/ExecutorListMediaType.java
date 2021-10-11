package ch.unisg.tapasexecutorpool.executorpool.adapter.in.web;

import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class ExecutorListMediaType {
    public static final String EXECUTOR_LIST_MEDIA_TYPE = "application/json";

    public static JSONObject toJSON(List<Executor> executorList) throws JSONException {
        JSONObject payload = new JSONObject();

        List<JSONObject> executorsAsJSONObjects = new ArrayList<>();
        for (Executor executor: executorList){
            executorsAsJSONObjects.add(ExecutorMediaType.toJSON(executor));
        }
        payload.put("executorList", executorsAsJSONObjects);
        return payload;
    }

    public static String serialize(List<Executor> executorList) throws JSONException {
        return toJSON(executorList).toString();
    }

    private ExecutorListMediaType() { }
}
