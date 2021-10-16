package ch.unisg.tapasexecutors.executors.domain;

import org.json.JSONObject;

final public class TaskMediaType {
    public static final String TASK_MEDIA_TYPE = "application/json";

    public static String serialize(Task task) {
        JSONObject payload = new JSONObject();

        payload.put("taskId", task.getTaskId().getValue());
        payload.put("taskListName", task.getTaskListName().getValue());

        return payload.toString();
    }

    private TaskMediaType() { }
}
