package ch.unisg.roster.roster.application.port.out;

import ch.unisg.roster.roster.domain.valueobject.ExecutorType;

public interface GetAllExecutorInExecutorPoolByTypePort {
    /**
    *   Checks if a executor with the given type exist in our executor pool
    *   @return boolean
    **/
    boolean doesExecutorTypeExist(ExecutorType type);
}


