package ch.unisg.tapastasks.tasks.adapter.in.web;

import ch.unisg.tapastasks.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.TaskList;
import org.json.JSONObject;

final public class TaskMediaType {
    public static final String TASK_MEDIA_TYPE = "application/json";

    public static String serialize(Task task) {
        JSONObject payload = new JSONObject();

        payload.put("taskId", task.getTaskId().getValue());
        payload.put("taskName", task.getTaskName().getValue());
        payload.put("taskType", task.getTaskType().getValue());
        payload.put("taskState", task.getTaskState().getValue());
        payload.put("taskListName", TaskList.getTapasTaskList().getTaskListName().getValue());
        payload.put("taskResult", task.getTaskResult().getValue());
        return payload.toString();
    }

    private TaskMediaType() { }
}
