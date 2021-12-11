package ch.unisg.tapastasks.tasks.application.service;

import ch.unisg.tapastasks.tasks.application.port.out.TaskListLock;
import org.springframework.stereotype.Component;

@Component
public class NoOpTaskListLock implements TaskListLock {
    @Override
    public void lockTaskList(String taskListName) {
        //do nothing
    }

    @Override
    public void releaseTaskList(String taskListName) {
        //do nothing
    }
}
