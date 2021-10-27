package ch.unisg.assignment.assignment.domain;

import ch.unisg.assignment.assignment.domain.valueobject.ExecutorType;
import ch.unisg.common.valueobject.ExecutorURI;
import lombok.Getter;
import lombok.Setter;

public class ExecutorInfo {
    @Getter
    @Setter
    private ExecutorURI executorURI;

    @Getter
    @Setter
    private ExecutorType executorType;
}
