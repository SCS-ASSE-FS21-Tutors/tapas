package ch.unisg.executorBase.executor.domain;

import ch.unisg.executorBase.executor.application.port.out.ExecutionFinishedEventPort;
import ch.unisg.executorBase.executor.application.port.out.GetAssignmentPort;
import ch.unisg.executorBase.executor.application.port.out.NotifyExecutorPoolPort;

import ch.unisg.executorBase.executor.adapter.out.web.ExecutionFinishedEventAdapter;
import ch.unisg.executorBase.executor.adapter.out.web.GetAssignmentAdapter;
import ch.unisg.executorBase.executor.adapter.out.web.NotifyExecutorPoolAdapter;
import ch.unisg.executorBase.executor.application.service.NotifyExecutorPoolService;
import lombok.Getter;

public abstract class ExecutorBase {

    @Getter
    private String ip;

    @Getter
    private ExecutorType executorType;

    @Getter
    private int port;

    @Getter
    private ExecutorStatus status;

    // TODO Violation of the Dependency Inversion Principle?, but we havn't really got a better solutions to send a http request / access a service from a domain model
    // TODO I guess we can somehow autowire this but I don't know why it's not working :D
    private final NotifyExecutorPoolPort notifyExecutorPoolPort = new NotifyExecutorPoolAdapter();
	private final NotifyExecutorPoolService notifyExecutorPoolService = new NotifyExecutorPoolService(notifyExecutorPoolPort);
    private final GetAssignmentPort getAssignmentPort = new GetAssignmentAdapter();
    private final ExecutionFinishedEventPort executionFinishedEventPort = new ExecutionFinishedEventAdapter();

    public ExecutorBase(ExecutorType executorType) {
        System.out.println("Starting Executor");
        // TODO set this automaticly
        this.ip = "localhost";
        this.port = 8084;
        this.executorType = executorType;

        this.status = ExecutorStatus.STARTING_UP;
        if(!notifyExecutorPoolService.notifyExecutorPool(this.ip, this.port, this.executorType)) {
            System.exit(0);
        } else {
            this.status = ExecutorStatus.IDLING;
            getAssignment();
        }
    }

    public void getAssignment() {
        Task newTask = getAssignmentPort.getAssignment(this.getExecutorType(), this.getIp(),
            this.getPort());
        if (newTask != null) {
            this.executeTask(newTask);
        } else {
            this.status = ExecutorStatus.IDLING;
        }
    }

    private void executeTask(Task task) {
        System.out.println("Starting execution");
        this.status = ExecutorStatus.EXECUTING;

        task.setResult(execution());

        executionFinishedEventPort.publishExecutionFinishedEvent(
            new ExecutionFinishedEvent(task.getTaskID(), task.getResult(), "SUCCESS"));

        System.out.println("Finish execution");
        getAssignment();
    }

    protected abstract String execution();

}
