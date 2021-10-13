package ch.unisg.executorBase.executor.application.port.in;

public interface TaskAvailableUseCase {
    void newTaskAvailable(TaskAvailableCommand command);
}
