package ch.unisg.tapasexecutor.application.ports.in;

import ch.unisg.tapasexecutor.domain.Task;

public interface IsTaskAcceptableQuery {
    boolean isAcceptable(Task task);
}
