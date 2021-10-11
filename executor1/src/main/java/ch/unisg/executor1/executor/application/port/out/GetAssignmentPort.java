package ch.unisg.executor1.executor.application.port.out;

import ch.unisg.executor1.executor.domain.ExecutorType;
import ch.unisg.executor1.executor.domain.Task;

public interface GetAssignmentPort {
    Task getAssignment(ExecutorType executorType);
}
