package ch.unisg.tapas.auctionhouse.application.port.in;

public interface TaskWonUseCase {
    boolean addWonTaskToTaskList(TaskWonCommand command);
}
