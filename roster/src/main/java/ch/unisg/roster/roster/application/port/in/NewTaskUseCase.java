package ch.unisg.roster.roster.application.port.in;

public interface NewTaskUseCase {
    boolean addNewTaskToQueue(NewTaskCommand newTaskCommand);
}
