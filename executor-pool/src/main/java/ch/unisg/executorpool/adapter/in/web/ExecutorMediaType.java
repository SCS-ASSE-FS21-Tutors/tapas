package ch.unisg.executorpool.adapter.in.web;

import ch.unisg.executorpool.domain.ExecutorClass;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

final public class ExecutorMediaType {
    public static final String EXECUTOR_MEDIA_TYPE = "application/json";

    public static String serialize(ExecutorClass executorClass) {
        JSONObject payload = new JSONObject();

        payload.put("executorIp", executorClass.getExecutorIp().getValue());
        payload.put("executorPort", executorClass.getExecutorPort().getValue());
        payload.put("executorTaskType", executorClass.getExecutorTaskType().getValue());

        return payload.toString();
    }

    public static String serialize(List<ExecutorClass> listOfExecutors) {
        String serializedList = "[ \n";

        for (ExecutorClass executor: listOfExecutors) {
            serializedList += serialize(executor) + "\n";
        }

        // return serializedList + "\n ]";
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("executorIp", "localhost");
        jsonArray.put(jsonObject);
        return jsonArray.toString();
    }

    private ExecutorMediaType() { }
}
