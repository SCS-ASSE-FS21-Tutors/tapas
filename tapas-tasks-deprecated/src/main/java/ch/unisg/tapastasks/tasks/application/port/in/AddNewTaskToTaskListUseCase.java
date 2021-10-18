package ch.unisg.tapastasks.tasks.application.port.in;

import ch.unisg.tapastasks.tasks.domain.Task;

public interface AddNewTaskToTaskListUseCase {
    Task addNewTaskToTaskList(AddNewTaskToTaskListCommand command);
}
