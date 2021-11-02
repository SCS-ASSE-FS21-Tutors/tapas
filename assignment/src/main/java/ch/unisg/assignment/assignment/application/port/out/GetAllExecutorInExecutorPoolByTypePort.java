package ch.unisg.assignment.assignment.application.port.out;

import ch.unisg.assignment.assignment.domain.valueobject.ExecutorType;

public interface GetAllExecutorInExecutorPoolByTypePort {
    /**
    *   Checks if a executor with the given type exist in our executor pool
    *   @return boolean
    **/
    boolean doesExecutorTypeExist(ExecutorType type);
}


