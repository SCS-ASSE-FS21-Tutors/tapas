package ch.unisg.executorBase.executor.application.port.out;

import ch.unisg.executorBase.executor.domain.ExecutorType;
import ch.unisg.executorBase.executor.domain.Task;

public interface GetAssignmentPort {
    Task getAssignment(ExecutorType executorType);
}
