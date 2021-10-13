package ch.unisg.tapasexecutorrobot.executor.adapter.in.web;

import ch.unisg.tapasexecutorrobot.executor.domain.Task;
import org.json.JSONObject;

final public class TaskMediaType {
    public static final String TASK_MEDIA_TYPE = "application/json";

    public static String serialize(Task task) {
        JSONObject payload = new JSONObject();

        payload.put("taskId", task.getTaskId().getValue());
        payload.put("taskName", task.getTaskName().getValue());
        payload.put("taskType", task.getTaskType().getValue());
        payload.put("taskState", task.getTaskState().getValue());
        payload.put("taskPayload", task.getTaskPayload().getValue());
        
        return payload.toString();
    }

    private TaskMediaType() { }
}
