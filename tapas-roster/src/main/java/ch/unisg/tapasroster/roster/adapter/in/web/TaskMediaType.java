package ch.unisg.tapasroster.roster.adapter.in.web;

import ch.unisg.tapasroster.roster.domain.Task;
import org.json.JSONException;
import org.json.JSONObject;


final public class TaskMediaType {
    public static final String TASK_MEDIA_TYPE = "application/json";

    public static String serialize(Task task) throws JSONException {
        JSONObject payload = new JSONObject();

        payload.put("taskId", task.getTaskId().getValue());
        payload.put("taskType", task.getTaskType().getValue());
        return payload.toString();
    }

    private TaskMediaType() { }
}