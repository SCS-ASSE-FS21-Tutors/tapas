package ch.unisg.assignment.assignment.application.port.in;

public interface NewTaskUseCase {
    boolean addNewTaskToQueue(NewTaskCommand newTaskCommand);
}
