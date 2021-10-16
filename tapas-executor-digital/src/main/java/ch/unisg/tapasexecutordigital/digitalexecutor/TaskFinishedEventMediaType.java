package ch.unisg.tapasexecutordigital.digitalexecutor;

import org.json.JSONObject;

final public class TaskFinishedEventMediaType {
    public static final String MEDIA_TYPE = "application/json";

    public static String serialize(TaskFinishedEvent event) {
        JSONObject payload = new JSONObject();

        payload.put("taskId", event.getTaskId());
        payload.put("taskListName", event.getTaskListName());
        payload.put("result", event.getResult());

        return payload.toString();
    }

    private TaskFinishedEventMediaType() { }
}
