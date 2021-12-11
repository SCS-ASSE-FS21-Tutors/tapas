package ch.unisg.tapastasks.tasks.application.port.out;

import ch.unisg.tapastasks.tasks.domain.Task;

import java.util.List;

public interface LoadTaskListPort {

    List<Task> loadTaskList();
}
