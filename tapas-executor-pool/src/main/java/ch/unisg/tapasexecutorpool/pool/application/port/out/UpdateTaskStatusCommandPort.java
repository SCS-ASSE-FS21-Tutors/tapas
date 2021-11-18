package ch.unisg.tapasexecutorpool.pool.application.port.out;

public interface UpdateTaskStatusCommandPort {
    boolean updateTaskStatus(UpdateTaskStatusCommand command);
}
