package ch.unisg.tapasexecutorpool.pool.application.port.in;

import ch.unisg.tapasexecutorpool.pool.domain.Task;

public interface CanExecuteTaskQuery {

    boolean canExecute(Task task);
}
