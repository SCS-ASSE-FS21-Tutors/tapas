package ch.unisg.executor1.executor.domain;

import ch.unisg.executor1.executor.application.port.out.ExecutionFinishedEventPort;
import ch.unisg.executor1.executor.application.port.out.GetAssignmentPort;
import ch.unisg.executor1.executor.application.port.out.NotifyExecutorPoolPort;

import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import ch.unisg.executor1.executor.adapter.out.web.ExecutionFinishedAdapter;
import ch.unisg.executor1.executor.adapter.out.web.GetAssignmentAdapter;
import ch.unisg.executor1.executor.adapter.out.web.NotifyExecutorPoolAdapter;
import ch.unisg.executor1.executor.application.service.NotifyExecutorPoolService;
import lombok.Getter;

public class Executor {

    @Getter
    private String ip;

    @Getter
    private String executorType = "addition";

    @Getter
    private int port;

    @Getter
    private ExecutorStatus status;

    private static final Executor executor = new Executor();

    // TODO Violation of the Dependency Inversion Principle?, but we havn't really got a better solutions to send a http request / access a service from a domain model
    // TODO I guess we can somehow autowire this but I don't know why it's not working :D
    private final NotifyExecutorPoolPort notifyExecutorPoolPort = new NotifyExecutorPoolAdapter();
	private final NotifyExecutorPoolService notifyExecutorPoolService = new NotifyExecutorPoolService(notifyExecutorPoolPort);
    private final GetAssignmentPort getAssignmentPort = new GetAssignmentAdapter();
    private final ExecutionFinishedEventPort executionFinishedEventPort = new ExecutionFinishedAdapter();

    private Executor() {
        System.out.println("Starting Executor");
        // TODO set this automaticly
        this.ip = "localhost";
        this.port = 8084;
    
        this.status = ExecutorStatus.STARTING_UP;
        if(!notifyExecutorPoolService.notifyExecutorPool(this.ip, this.port, this.executorType)) {
            System.exit(0);
        } else {
            System.out.println(true);
            this.status = ExecutorStatus.IDLING;
            getAssignment();
        }
    }

    public static Executor getExecutor() {
        return executor;
    }

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
        int a = 10;
        int b = 20;
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int result = a + b;

        task.setResult(Integer.toString(result));

        executionFinishedEventPort.publishExecutionFinishedEvent(new ExecutionFinishedEvent(task.getTaskID(), task.getResult(), "SUCCESS"));

        System.out.println("Finish execution");
        getAssignment();
    }
    
}
