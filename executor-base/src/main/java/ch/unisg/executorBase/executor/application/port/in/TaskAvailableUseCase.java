package ch.unisg.executorbase.executor.application.port.in;

public interface TaskAvailableUseCase {
    void newTaskAvailable(TaskAvailableCommand command);
}
