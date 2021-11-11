package ch.unisg.tapas.roster.application.port.out;

import ch.unisg.tapas.roster.entities.Task;

public interface ExecutorPoolPort {

    boolean canExecuteInternally(Task task);
    void executeInternally(Task task);

}
