package ch.unisg.tapasexecutorbase.executor.application.port.in;

public interface RegisterExecutorToPoolUseCase {
    boolean registerToPool(RegisterExecutorToPoolCommand command);
}
