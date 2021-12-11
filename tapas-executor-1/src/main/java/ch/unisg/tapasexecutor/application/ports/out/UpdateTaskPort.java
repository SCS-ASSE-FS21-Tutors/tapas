package ch.unisg.tapasexecutor.application.ports.out;

import ch.unisg.tapasexecutor.domain.Task;

public interface UpdateTaskPort {
    void setTaskComplete(Task task);
}
