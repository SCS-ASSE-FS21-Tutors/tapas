package ch.unisg.assignment.assignment.application.port.out;

import ch.unisg.assignment.assignment.domain.valueobject.ExecutorType;

public interface GetAllExecutorInExecutorPoolByTypePort {
    boolean doesExecutorTypeExist(ExecutorType type);
}


