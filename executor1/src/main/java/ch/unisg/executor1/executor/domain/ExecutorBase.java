package ch.unisg.executor1.executor.domain;

import ch.unisg.executor1.executor.application.port.out.ExecutionFinishedEventPort;
import ch.unisg.executor1.executor.application.port.out.GetAssignmentPort;
import ch.unisg.executor1.executor.application.port.out.NotifyExecutorPoolPort;

import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import ch.unisg.executor1.executor.adapter.out.web.ExecutionFinishedEventAdapter;
import ch.unisg.executor1.executor.adapter.out.web.GetAssignmentAdapter;
import ch.unisg.executor1.executor.adapter.out.web.NotifyExecutorPoolAdapter;
import ch.unisg.executor1.executor.application.service.NotifyExecutorPoolService;
import lombok.Getter;

abstract class ExecutorBase {

    @Getter
    private String ip;

    @Getter
    private ExecutorType executorType;

    @Getter
    private int port;

    @Getter
    private ExecutorStatus status;

    // private static final ExecutorBase executor = new ExecutorBase();

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
            System.out.println(true);
            this.status = ExecutorStatus.IDLING;
            getAssignment();
        }
    }

    // public static ExecutorBase getExecutor() {
    //     return executor;
    // }

    public void getAssignment() {
        Task newTask = getAssignmentPort.getAssignment(this.getExecutorType());
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

        executionFinishedEventPort.publishExecutionFinishedEvent(new ExecutionFinishedEvent(task.getTaskID(), task.getResult(), "SUCCESS"));

        System.out.println("Finish execution");
        getAssignment();
    }

    abstract String execution();
    
}
