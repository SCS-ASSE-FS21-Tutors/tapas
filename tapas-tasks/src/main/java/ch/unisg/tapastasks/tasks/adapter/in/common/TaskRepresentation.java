package ch.unisg.tapastasks.tasks.adapter.in.common;

import ch.unisg.tapastasks.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.TaskList;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import java.util.Optional;

/**
 * TODO Andrei: add comments
 */
final public class TaskRepresentation {
    // The media type used for this task representation format. The media type is just an identifier
    // and can be registered with IANA to support interoperability.
    public static final String TASK_MEDIA_TYPE = "application/task+json";

    @Getter @Setter
    private String taskId;
    @Getter
    private final String taskName;
    @Getter
    private final String taskType;

    @Getter @Setter
    private Optional<String> originalTaskUri;
    @Getter @Setter
    private Optional<String> serviceProvider;

    @Getter @Setter
    private Optional<String> inputData;
    @Getter @Setter
    private Optional<String> outputData;

    public TaskRepresentation(String taskName, String taskType) {
        this.taskName = taskName;
        this.taskType = taskType;

        this.originalTaskUri = Optional.empty();
        this.serviceProvider = Optional.empty();
        this.inputData = Optional.empty();
        this.outputData = Optional.empty();
    }

//    public static TaskRepresentation deserialize(String payload) {
//        JSONObject taskObj = new JSONObject(payload);
//
//        String taskName = taskObj.getString("taskName");
//        String taskType = taskObj.getString("taskType");
//
//        // If the task is a delegated task, then it contains a reference to the original task
//        Task.OriginalTaskUri originalTaskUri = null;
//        try {
//            String shadowOf = taskObj.getString("originalTaskUri");
//            originalTaskUri = new Task.OriginalTaskUri(shadowOf);
//        } catch (JSONException e) { }
//
//        // A task may have a domain-specific input string
//        Task.Input input = null;
//        try {
//            String inputString = taskObj.getString("input");
//            input = new Task.Input(inputString);
//        } catch (JSONException e) { }
//
//        Task task = new Task(new Task.TaskName((taskName)), new Task.TaskType(taskType),
//            originalTaskUri, input);
//
//        // A task may have a service provider, but not necessarily in all states (e.g., before a task
//        // is assigned)
//        try {
//            String serviceProvider = taskObj.getString("serviceProvider");
//            task.setProvider(new Task.ServiceProvider(serviceProvider));
//        } catch (JSONException e) { }
//
//        // A task may have a domain-specific output string
//        try {
//            String outputString = taskObj.getString("output");
//            task.setOutput(new Task.Output(outputString));
//        } catch (JSONException e) { }
//
//        return task;
//    }

    // TODO: refactor task serialization
    public static String serialize(Task task) {
        JSONObject payload = new JSONObject();

        payload.put("taskId", task.getTaskId().getValue());
        payload.put("taskName", task.getTaskName().getValue());
        payload.put("taskType", task.getTaskType().getValue());
        payload.put("taskState", task.getTaskStatus().getValue());
        payload.put("taskListName", TaskList.getTapasTaskList().getTaskListName().getValue());

        return payload.toString();
    }
}
