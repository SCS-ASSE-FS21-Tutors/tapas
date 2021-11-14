package ch.unisg.tapasroster.roster.application.service;

import ch.unisg.tapasroster.roster.application.port.in.ScheduleTaskCommand;
import ch.unisg.tapasroster.roster.application.port.in.ScheduleTaskUseCase;
import ch.unisg.tapasroster.roster.application.port.out.ForwardTaskToAuctionHouseEventPort;
import ch.unisg.tapasroster.roster.application.port.out.ForwardTaskToExecutorPoolEventPort;
import ch.unisg.tapasroster.roster.domain.ForwardTaskToAuctionHouseEvent;
import ch.unisg.tapasroster.roster.domain.ForwardTaskToExecutorPoolEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Random;

@RequiredArgsConstructor
@Component
@Transactional
public class ScheduleTaskService implements ScheduleTaskUseCase {

    private final ForwardTaskToExecutorPoolEventPort forwardTaskToExecutorPoolEventPort;
    private final ForwardTaskToAuctionHouseEventPort forwardTaskToAuctionHouseEventPort;

    private final Random random = new Random();

    @Override
    public boolean scheduleTask(ScheduleTaskCommand command) {
        var task = command.getTask();
        System.out.println("Schedule new task");
        System.out.println(task.getTaskName().getValue());
        System.out.println(task.getTaskId().getValue());
        System.out.println(task.getTaskType().getValue());
        System.out.println(task.getInputData().getValue());

        switch (task.getTaskType().getValue()) {
            case BIGROBOT:
            case COMPUTATION:
                System.out.println("Forward Task to Executor Pool");
                var eventExecutorPool = new ForwardTaskToExecutorPoolEvent(task);
                forwardTaskToExecutorPoolEventPort.forwardTaskToExecutorPoolEvent(eventExecutorPool);
                return true;
            case SMALLROBOT:
            case RANDOMTEXT:
                System.out.println("Forward Task to Auction House");
                var deadline = random.nextInt(10000 + 1) + 100000;
                var eventAuctionHouse = new ForwardTaskToAuctionHouseEvent(task, deadline);
                forwardTaskToAuctionHouseEventPort.forwardTaskToAuctionHouseEvent(eventAuctionHouse);
                return true;
            default:
                System.out.println("Unknown Task Type");
                return false;
        }
    }
}
