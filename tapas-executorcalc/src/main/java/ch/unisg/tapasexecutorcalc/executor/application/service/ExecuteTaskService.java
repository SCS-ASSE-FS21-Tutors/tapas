package ch.unisg.tapasexecutorcalc.executor.application.service;

import ch.unisg.tapasexecutorcalc.executor.application.port.in.ExecuteTaskCommand;
import ch.unisg.tapasexecutorcalc.executor.application.port.in.ExecuteTaskUseCase;
import ch.unisg.tapasexecutorcalc.executor.domain.Task;
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
    @Override
    public Task executeTask(ExecuteTaskCommand command) {
        var task = command.getTask();

        var expression = command.getTask().getTaskPayload().getValue();
        System.out.println("Math Expression: " + expression);
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        try{
            System.out.println("Result: " + engine.eval(expression));
        } catch(ScriptException ex) {
            System.out.println("Invalid math expression");
        }

        return task;
    }
}
