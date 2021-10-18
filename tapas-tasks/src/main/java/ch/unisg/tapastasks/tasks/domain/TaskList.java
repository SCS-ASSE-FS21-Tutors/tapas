package ch.unisg.tapastasks.tasks.domain;

import lombok.Getter;
import lombok.Value;

import javax.swing.text.html.Option;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


/**This is our aggregate root**/
public class TaskList {

    @Getter
    private final TaskListName taskListName;

    @Getter
    private final ListOfTasks listOfTasks;

    //Note: We do not care about the management of task lists, there is only one within this service
    //--> using the Singleton pattern here to make lives easy; we will later load it from a repo
    //TODO change "tutors" to your group name ("groupx")
    private static final TaskList taskList = new TaskList(new TaskListName("tapas-tasks-tutors"));

    private TaskList(TaskListName taskListName) {
        this.taskListName = taskListName;
        this.listOfTasks = new ListOfTasks(new LinkedList<Task>());
    }

    public static TaskList getTapasTaskList() {
        return taskList;
    }

    //Only the aggregate root is allowed to create new tasks and add them to the task list.
    //Note: Here we could add some sophisticated invariants/business rules that the aggregate root checks
    public Task addNewTaskWithNameAndType(Task.TaskName name, Task.TaskType type) {
        Task newTask = Task.createTaskWithNameAndType(name,type);
        listOfTasks.value.add(newTask);
        //This is a simple debug message to see that the task list is growing with each new request
        System.out.println("Number of tasks: "+listOfTasks.value.size());
        //Here we would also publish a domain event to other entities in the core interested in this event.
        //However, we skip this here as it makes the core even more complex (e.g., we have to implement a light-weight
        //domain event publisher and subscribers (see "Implementing Domain-Driven Design by V. Vernon, pp. 296ff).
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
