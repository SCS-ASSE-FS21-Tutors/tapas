package ch.unisg.tapasexecutorpool.pool.application.port.out;

import ch.unisg.tapascommon.pool.domain.Executor;
import ch.unisg.tapasexecutorpool.pool.domain.ExecutorPool;

public interface LoadExecutorFromRepositoryPort {
    Executor loadExecutor(Executor.ExecutorId executorId, ExecutorPool.ExecutorPoolName executorPoolName);
}
