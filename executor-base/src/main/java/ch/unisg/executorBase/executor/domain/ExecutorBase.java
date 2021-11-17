package ch.unisg.executorbase.executor.domain;

import java.util.logging.Level;
import java.util.logging.Logger;

import ch.unisg.common.valueobject.ExecutorURI;
import ch.unisg.executorbase.executor.adapter.out.web.ExecutionFinishedEventAdapter;
import ch.unisg.executorbase.executor.adapter.out.web.GetAssignmentAdapter;
import ch.unisg.executorbase.executor.adapter.out.web.NotifyExecutorPoolAdapter;
import ch.unisg.executorbase.executor.application.port.out.ExecutionFinishedEventPort;
import ch.unisg.executorbase.executor.application.port.out.GetAssignmentPort;
import ch.unisg.executorbase.executor.application.port.out.NotifyExecutorPoolPort;
import ch.unisg.executorbase.executor.application.service.NotifyExecutorPoolService;
import lombok.Getter;

public abstract class ExecutorBase {

    @Getter
    private ExecutorURI executorURI;

    @Getter
    private ExecutorType executorType;

    @Getter
    private ExecutorStatus status;

    // TODO Violation of the Dependency Inversion Principle?,
    // TODO do this with only services
    private final NotifyExecutorPoolPort notifyExecutorPoolPort = new NotifyExecutorPoolAdapter();
	private final NotifyExecutorPoolService notifyExecutorPoolService = new NotifyExecutorPoolService(notifyExecutorPoolPort);
    private final GetAssignmentPort getAssignmentPort = new GetAssignmentAdapter();
    private final ExecutionFinishedEventPort executionFinishedEventPort = new ExecutionFinishedEventAdapter();

    Logger logger = Logger.getLogger(ExecutorBase.class.getName());

    protected ExecutorBase(ExecutorType executorType) {
        logger.info("Starting Executor");
        this.status = ExecutorStatus.STARTING_UP;
        this.executorType = executorType;
        // TODO set this automaticly
        this.executorURI = new ExecutorURI("http://localhost:8084");
        // TODO do this in main
        // Notify executor-pool about existence. If executor-pools response is successfull start with getting an assignment, else shut down executor.
        if(!notifyExecutorPoolService.notifyExecutorPool(this.executorURI, this.executorType)) {
            logger.log(Level.WARNING, "Executor could not connect to executor pool! Shuting down!");
            System.exit(0);
        } else {
            logger.info("Executor conntected to executor pool");
            this.status = ExecutorStatus.IDLING;
            getAssignment();
        }
    }

    /**
    *   Requests a new task from the task queue
    *   @return void
    **/
    public void getAssignment() {
        Task newTask = getAssignmentPort.getAssignment(this.getExecutorType(), this.getExecutorURI());
        if (newTask != null) {
            logger.info("Executor got a new task");
            this.executeTask(newTask);
        } else {
            logger.info("Executor got no new task");
            this.status = ExecutorStatus.IDLING;
        }
    }

    /**
    *   Start the execution of a task
    *   @return void
    **/
    private void executeTask(Task task) {
        logger.info("Starting execution");
        this.status = ExecutorStatus.EXECUTING;

        task.setOutputData(execution(task.getInputData()));

        // TODO implement logic if execution was not successful
        executionFinishedEventPort.publishExecutionFinishedEvent(
            new ExecutionFinishedEvent(task.getTaskID(), task.getOutputData(), "SUCCESS"));

        logger.info("Finish execution");
        getAssignment();
    }

    /**
    *   Implementation of the actual execution method of an executor
    *   @return the execution result
    **/
    protected abstract String execution(String input);

}
