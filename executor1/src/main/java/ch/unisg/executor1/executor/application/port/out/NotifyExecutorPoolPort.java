package ch.unisg.executor1.executor.application.port.out;

import ch.unisg.executor1.executor.domain.ExecutorType;

public interface NotifyExecutorPoolPort {
    boolean notifyExecutorPool(String ip, int port, ExecutorType executorType);
}
