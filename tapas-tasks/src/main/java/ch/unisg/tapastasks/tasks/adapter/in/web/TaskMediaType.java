package ch.unisg.tapastasks.tasks.adapter.in.web;

import ch.unisg.tapastasks.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.TaskList;
import org.json.JSONObject;

/**
 * TODO Andrei: add comments
 */
final public class TaskMediaType {
    public static final String TASK_MEDIA_TYPE = "application/task+json";

    public static Task deserialize(String payload) {
        JSONObject taskObj = new JSONObject(payload);

        String taskName = taskObj.getString("taskName");
        String taskType = taskObj.getString("taskType");

        Task task = new Task(new Task.TaskName((taskName)), new Task.TaskType(taskType));

//        try {
//            String shadowOf = taskObj.getString("shadowOf");
//            task.setShadowOf(shadowOf);
//        } catch (JSONException e) { }
//
//        try {
//            String executedBy = taskObj.getString("executedBy");
//            task.setExecutedBy(executedBy);
//        } catch (JSONException e) { }

        return task;
    }

    public static String serialize(Task task) {
        JSONObject payload = new JSONObject();

        payload.put("taskId", task.getTaskId().getValue());
        payload.put("taskName", task.getTaskName().getValue());
        payload.put("taskType", task.getTaskType().getValue());
        payload.put("taskState", task.getTaskState().getValue());
        payload.put("taskListName", TaskList.getTapasTaskList().getTaskListName().getValue());

        return payload.toString();
    }

    private TaskMediaType() { }
}
