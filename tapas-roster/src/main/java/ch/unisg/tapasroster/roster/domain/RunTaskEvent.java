package ch.unisg.tapasroster.roster.domain;


import ch.unisg.tapasroster.executorpool.domain.Executor;
import lombok.Getter;

public class RunTaskEvent {

    @Getter
    private final String taskId;

    @Getter
    private final String taskListName;

    @Getter
    private final String executorServer;

    @Getter
    private final String executorPort;

    @Getter
    private final String executorName;

    public RunTaskEvent(Task task, Executor executor) {
        this.taskId = task.getTaskId().getValue();
        this.taskListName = task.getTaskListName().getValue();
        this.executorServer = executor.getExecutorServer().getValue();
        this.executorPort = executor.getExecutorPort().getValue();
        this.executorName = executor.getExecutorName().getValue();
    }

    public String getURI() {
        return executorServer + ":" + executorPort + "/" + executorName.trim().toLowerCase() +"/runtask";
    }
}
