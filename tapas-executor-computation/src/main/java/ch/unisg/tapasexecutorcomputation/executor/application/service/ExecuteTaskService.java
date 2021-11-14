package ch.unisg.tapasexecutorcomputation.executor.application.service;

import ch.unisg.tapascommon.tasks.adapter.in.formats.TaskJsonRepresentation;
import ch.unisg.tapascommon.tasks.domain.Task;
import ch.unisg.tapasexecutorbase.executor.application.port.in.ExecuteTaskCommand;
import ch.unisg.tapasexecutorbase.executor.application.port.in.ExecuteTaskUseCase;
import ch.unisg.tapasexecutorbase.executor.application.port.out.ExecutorStateChangedEventPort;
import ch.unisg.tapasexecutorbase.executor.application.port.out.TaskUpdatedEventPort;
import ch.unisg.tapasexecutorbase.executor.application.service.ExecuteTaskBaseService;
import ch.unisg.tapasexecutorbase.executor.domain.ExecutorStateChangedEvent;
import ch.unisg.tapasexecutorbase.executor.domain.TaskUpdatedEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@AllArgsConstructor
@Primary
@Component
public class ExecuteTaskService implements ExecuteTaskUseCase {

    private final ExecutorStateChangedEventPort executorStateChangedEventPort;
    private final TaskUpdatedEventPort taskUpdatedEventPort;

    private void updateExecutorState(String state) {
        executorStateChangedEventPort.publishExecutorStateChangedEvent(
                new ExecutorStateChangedEvent(Task.Type.COMPUTATION.name(), state)
        );
    }

    private String calculate(String expression) {
        var engine = new ScriptEngineManager().getEngineByName("JavaScript");
        try {
            return engine.eval(expression).toString();
        } catch (ScriptException e) {
            return "INVALID EXPRESSION";
        }
    }

    @Override
    public void executeTask(ExecuteTaskCommand command) {
        updateExecutorState("BUSY");

        var task = command.getTask();

        task.setTaskStatus(new Task.TaskStatus(Task.Status.RUNNING));
        ExecuteTaskBaseService.updateTaskStatus(task, taskUpdatedEventPort);

        var expression = task.getInputData().getValue();
        var result = calculate(expression);

        System.out.println("Math Expression: " + expression);
        System.out.println("Result: " + result);

        task.setOutputData(new Task.OutputData(result));
        task.setTaskStatus(new Task.TaskStatus(Task.Status.EXECUTED));
        ExecuteTaskBaseService.updateTaskStatus(task, taskUpdatedEventPort);

        updateExecutorState("IDLE");
    }
}
