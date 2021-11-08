package ch.unisg.executorbase.executor.application.port.out;

import ch.unisg.common.valueobject.ExecutorURI;
import ch.unisg.executorbase.executor.domain.ExecutorType;

public interface NotifyExecutorPoolPort {
    boolean notifyExecutorPool(ExecutorURI executorURI, ExecutorType executorType);
}
