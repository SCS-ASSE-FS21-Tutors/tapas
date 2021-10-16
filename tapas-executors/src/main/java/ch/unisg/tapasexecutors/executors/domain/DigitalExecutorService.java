package ch.unisg.tapasexecutors.executors.domain;


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
        System.out.println("run task async");
        String result = digitalExecutor.runTask();
        System.out.println("run task async 2");
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println(i);

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("run task async 3");

        publishTaskFinishedEvent(task, result);
    }

    private void publishTaskFinishedEvent(Task task, String result) {
        DigitalExecutorAdapter.publishTaskFinishedEvent(new TaskFinishedEvent(task));
    }
}
