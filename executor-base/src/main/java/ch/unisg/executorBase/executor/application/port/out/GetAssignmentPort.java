package ch.unisg.executorbase.executor.application.port.out;

import ch.unisg.common.valueobject.ExecutorURI;
import ch.unisg.executorbase.executor.domain.ExecutorType;
import ch.unisg.executorbase.executor.domain.Task;

public interface GetAssignmentPort {
    Task getAssignment(ExecutorType executorType, ExecutorURI executorURI);
}
