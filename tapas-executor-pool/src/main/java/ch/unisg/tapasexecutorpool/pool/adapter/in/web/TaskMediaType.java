package ch.unisg.tapasexecutorpool.pool.adapter.in.web;
import ch.unisg.tapasexecutorpool.pool.domain.Task;
import org.json.JSONObject;

final public class TaskMediaType {
    public static final String TASK_MEDIA_TYPE = "application/json";

    public static String serialize(Task task) {
        JSONObject payload = new JSONObject();

        payload.put("taskId", task.getTaskId().getValue());
        payload.put("taskName", task.getTaskName().getValue());
        payload.put("taskType", task.getTaskType().getValue());

        return payload.toString();
    }

    private TaskMediaType() { }
}
