package ch.unisg.tapastasks.tasks.application.service;

import ch.unisg.tapastasks.tasks.application.port.out.AddTaskPort;
import ch.unisg.tapastasks.tasks.application.port.out.LoadTaskListPort;
import ch.unisg.tapastasks.tasks.application.port.out.NewTaskAddedEventPort;
import ch.unisg.tapastasks.tasks.application.port.out.TaskListLock;
import ch.unisg.tapastasks.tasks.domain.Task;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.BDDMockito.*;


public class AddNewTaskToTaskListServiceTest {

    private final AddTaskPort addTaskPort = Mockito.mock(AddTaskPort.class);
    private final TaskListLock taskListLock = Mockito.mock(TaskListLock.class);
    private final NewTaskAddedEventPort newTaskAddedEventPort = Mockito.mock(NewTaskAddedEventPort.class);
    private final AddNewTaskToTaskListService addNewTaskToTaskListService = new AddNewTaskToTaskListService(
        "tapas-tasks-group3", newTaskAddedEventPort, addTaskPort, taskListLock);

    @Test
    void addingSucceeds() {
/*
        Task newTask = givenATaskWithNameAndTypeAndURI(new Task.TaskName("test-task"),
            new Task.TaskType("test-type"), Optional.of(new Task.OriginalTaskUri("example.org")));

        TaskList taskList = givenAnEmptyTaskList(TaskList.getTapasTaskList());

        AddNewTaskToTaskListCommand addNewTaskToTaskListCommand = new AddNewTaskToTaskListCommand(newTask.getTaskName(),
            newTask.getTaskType(), Optional.ofNullable(newTask.getOriginalTaskUri()), newTask.getInputData());

        Task addedTask = addNewTaskToTaskListService.addNewTaskToTaskList(addNewTaskToTaskListCommand);

        assertThat(addedTask).isNotNull();
        assertThat(taskList.getListOfTasks().getValue()).hasSize(1);

        then(taskListLock).should().lockTaskList(eq(TaskList.getTapasTaskList().getTaskListName()));
        */
    }


    private Task givenATaskWithNameAndTypeAndURI(Task.TaskName taskName, Task.TaskType taskType,
                                                 Optional<Task.OriginalTaskUri> originalTaskUri) {
        Task task = Mockito.mock(Task.class);
        given(task.getTaskName()).willReturn(taskName);
        given(task.getTaskType()).willReturn(taskType);
        given(task.getOriginalTaskUri()).willReturn(originalTaskUri.get());
        return task;
    }

}
