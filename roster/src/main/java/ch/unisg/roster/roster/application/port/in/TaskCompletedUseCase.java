package ch.unisg.roster.roster.application.port.in;

public interface TaskCompletedUseCase {
    void taskCompleted(TaskCompletedCommand taskCompletedCommand);
}
