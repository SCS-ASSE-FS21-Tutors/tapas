package ch.unisg.tapas.auctionhouse.application.port.out;

public interface ExecuteExternalTaskCommandPort {
    boolean executeExternalTask(ExecuteExternalTaskCommand command);
}
