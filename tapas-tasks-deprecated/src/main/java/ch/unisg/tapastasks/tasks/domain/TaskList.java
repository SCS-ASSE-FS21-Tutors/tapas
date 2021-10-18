package ch.unisg.tapastasks.tasks.domain;

import lombok.Getter;
import lombok.Value;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class TaskList {

    @Getter
    private final TaskListName taskListName;

    @Getter
    private final ListOfTasks listOfTasks;

    private static final TaskList taskList = new TaskList(new TaskListName("tapas-tasks-group4"));

    private TaskList(TaskListName taskListName) {
        this.taskListName = taskListName;
        this.listOfTasks = new ListOfTasks(new LinkedList<Task>());
    }

    public static TaskList getTapasTaskList() {
        return taskList;
    }

    /*public Task addNewTaskWithNameAndType(Task.TaskName name, Task.TaskType type) {
        Task newTask = Task.createTaskWithNameAndType(name,type);
        listOfTasks.value.add(newTask);
        System.out.println("Number of tasks: " + listOfTasks.value.size());
        return newTask;
    }*/

    public Task addNewTaskWithPayload(Task.TaskName name, Task.TaskType type, Task.TaskPayload payload) {
        Task newTask = Task.createTaskWithPayload(name, type, payload);
        listOfTasks.value.add(newTask);
        System.out.println("Number of tasks: " + listOfTasks.value.size());
        return newTask;
    }

    public Optional<Task> retrieveTaskById(Task.TaskId id) {
        for (Task task : listOfTasks.value) {
            if (task.getTaskId().getValue().equalsIgnoreCase(id.getValue())) {
                return Optional.of(task);
            }
        }

        return Optional.empty();
    }

    @Value
    public static class TaskListName {
        private String value;
    }

    @Value
    public static class ListOfTasks {
        private List<Task> value;
    }
}
