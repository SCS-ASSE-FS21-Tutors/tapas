package ch.unisg.tapasexecutorpool.pool.application.port.out;

import ch.unisg.tapasexecutorpool.pool.domain.Executor;

public interface LoadExecutorPort {

    Executor loadExecutor(Executor.ExecutorId executorId);

}
