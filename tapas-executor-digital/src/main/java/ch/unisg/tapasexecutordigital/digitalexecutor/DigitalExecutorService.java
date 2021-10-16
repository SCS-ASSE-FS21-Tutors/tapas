package ch.unisg.tapasexecutordigital.digitalexecutor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class DigitalExecutorService {

    private final DigitalExecutor digitalExecutor;

    @Autowired
    public DigitalExecutorService(DigitalExecutor digitalExecutor) {
        this.digitalExecutor = digitalExecutor;
    }

    @Async
    public void runTaskAsync(Task task) {
        String result = digitalExecutor.runTask();

        publishTaskFinishedEvent(task, result);
    }

    private void publishTaskFinishedEvent(Task task, String result) {
        TaskFinishedEvent event = new TaskFinishedEvent(
                task.getTaskId().getValue(),
                task.getTaskListName().getValue(),
                result == null ? "" : result);
        DigitalExecutorAdapter.publishTaskFinishedEvent(event);
    }
}
