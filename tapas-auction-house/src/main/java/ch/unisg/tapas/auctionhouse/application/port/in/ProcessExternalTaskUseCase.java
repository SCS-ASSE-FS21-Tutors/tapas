package ch.unisg.tapas.auctionhouse.application.port.in;

public interface ProcessExternalTaskUseCase {
    boolean processExternalTask(ProcessExternalTaskCommand command);
}

