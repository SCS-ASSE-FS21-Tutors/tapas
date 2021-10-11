package ch.unisg.executor1.executor.application.port.in;

public interface TaskAvailableUseCase {
    void newTaskAvailable(TaskAvailableCommand command);
}
