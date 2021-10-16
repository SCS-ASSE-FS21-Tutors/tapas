package ch.unisg.tapasexecutorcherrybot.cherrybot;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CherrybotExecutorService {

    private final CherrybotExecutor cherrybotExecutor;

    @Autowired
    public CherrybotExecutorService(CherrybotExecutor cherrybotExecutor) {
        this.cherrybotExecutor = cherrybotExecutor;
    }

    @Async
    public void runTaskAsync(Task task) {
        String result = null;
        try {
            cherrybotExecutor.runTask();
            result = "SUCCESS: Task finished successfully";
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            result = "ERROR: Task run was unsuccessful";
        }

        publishTaskFinishedEvent(task, result);
    }

    private void publishTaskFinishedEvent(Task task, String result) {
        TaskFinishedEvent event = new TaskFinishedEvent(
                task.getTaskId().getValue(),
                task.getTaskListName().getValue(),
                result == null ? "" : result);
        CherrybotExecutorAdapter.publishTaskFinishedEvent(event);
    }
}
