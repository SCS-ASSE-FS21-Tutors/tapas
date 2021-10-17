package ch.unisg.executorBase.executor.application.port.out;

import ch.unisg.executorBase.executor.domain.ExecutorType;

public interface NotifyExecutorPoolPort {
    boolean notifyExecutorPool(String ip, int port, ExecutorType executorType);
}
