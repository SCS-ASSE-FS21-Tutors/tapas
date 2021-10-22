package ch.unisg.tapasexecutorcalc.executor.application.service;

import ch.unisg.tapasexecutorcalc.executor.application.port.in.ExecuteTaskCommand;
import ch.unisg.tapasexecutorcalc.executor.application.port.in.ExecuteTaskUseCase;
import ch.unisg.tapascommon.tasks.domain.Task;
import ch.unisg.tapasexecutorcalc.executor.application.port.out.ExecutorStateChangedEventPort;
import ch.unisg.tapasexecutorcalc.executor.domain.ExecutorStateChangedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class ExecuteTaskService implements ExecuteTaskUseCase {

    private final ExecutorStateChangedEventPort executorStateChangedEventPort;

    @Override
    public Task executeTask(ExecuteTaskCommand command) {
        var task = command.getTask();

        var expression = command.getTask().getInputData().getValue();
        System.out.println("Math Expression: " + expression);
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");

        try{
            var result = engine.eval(expression).toString();
            task.setOutputData(new Task.OutputData(result));
            System.out.println("Result: " + result);
        } catch(ScriptException ex) {
            System.out.println("Invalid math expression");
        }

        var event = new ExecutorStateChangedEvent("CALC", "IDLE");
        executorStateChangedEventPort.publishExecutorStateChangedEvent(event);

        return task;
    }
}
