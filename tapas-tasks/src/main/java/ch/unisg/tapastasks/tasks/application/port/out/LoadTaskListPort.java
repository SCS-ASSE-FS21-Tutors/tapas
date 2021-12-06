package ch.unisg.tapastasks.tasks.application.port.out;

import ch.unisg.tapastasks.tasks.domain.TaskList;

public interface LoadTaskListPort {

    TaskList loadTaskList(TaskList.TaskListName taskListName);

}
