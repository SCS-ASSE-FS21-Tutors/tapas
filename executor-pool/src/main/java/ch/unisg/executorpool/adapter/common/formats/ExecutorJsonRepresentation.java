package ch.unisg.executorpool.adapter.common.formats;

import ch.unisg.executorpool.domain.ExecutorClass;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ExecutorJsonRepresentation {
    public static final String EXECUTOR_MEDIA_TYPE = "application/json";

    @Getter @Setter
    private String executorUri;

    @Getter @Setter
    private String executorTaskType;

    // TODO Check if this need Setters. Also applies to AuctionJsonRepresentation
    public ExecutorJsonRepresentation(String executorUri, String executorTaskType){
        this.executorUri = executorUri;
        this.executorTaskType = executorTaskType;
    }

    public static String serialize(ExecutorClass executorClass) {
        JSONObject payload = new JSONObject();

        payload.put("executorUri", executorClass.getExecutorUri().getValue());
        payload.put("executorTaskType", executorClass.getExecutorTaskType().getValue());

        return payload.toString();
    }

    public static String serialize(List<ExecutorClass> listOfExecutors) {
        JSONArray jsonArray = new JSONArray();

        for (ExecutorClass executor: listOfExecutors) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("executorUri", executor.getExecutorUri().getValue());
            jsonObject.put("executorTaskType", executor.getExecutorTaskType().getValue());

            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();
    }

    private ExecutorJsonRepresentation() { }
}
