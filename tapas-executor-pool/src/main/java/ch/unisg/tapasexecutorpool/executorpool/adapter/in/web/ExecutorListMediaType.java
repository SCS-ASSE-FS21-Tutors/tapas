package ch.unisg.tapasexecutorpool.executorpool.adapter.in.web;

import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

final public class ExecutorListMediaType {
    public static final String EXECUTOR_LIST_MEDIA_TYPE = "application/json";

    public static JSONObject toJSON(List<Executor> executorList) throws JSONException {
        JSONObject payload = new JSONObject();

        JSONArray executorsAsJSONObjects = new JSONArray();
        for (Executor executor: executorList){
            executorsAsJSONObjects.put(ExecutorMediaType.toJSON(executor));
        }
        payload.put("executorList", executorsAsJSONObjects);
        return payload;
    }

    public static String serialize(List<Executor> executorList) throws JSONException {
        return toJSON(executorList).toString();
    }

    private ExecutorListMediaType() { }
}
