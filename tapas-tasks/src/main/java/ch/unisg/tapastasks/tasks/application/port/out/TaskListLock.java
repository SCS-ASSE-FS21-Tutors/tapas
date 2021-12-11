package ch.unisg.tapastasks.tasks.application.port.out;


public interface TaskListLock {

    void lockTaskList(String taskListName);

    void releaseTaskList(String taskListName);

}
