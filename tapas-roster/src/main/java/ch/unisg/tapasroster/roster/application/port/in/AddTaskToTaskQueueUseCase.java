package ch.unisg.tapasroster.roster.application.port.in;

public interface AddTaskToTaskQueueUseCase {
    void addNewTaskToTaskQueue(AddTaskToTaskQueueCommand command);
}
