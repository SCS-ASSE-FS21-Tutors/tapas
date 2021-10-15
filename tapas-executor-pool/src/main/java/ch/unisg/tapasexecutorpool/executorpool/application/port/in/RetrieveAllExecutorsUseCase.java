package ch.unisg.tapasexecutorpool.executorpool.application.port.in;

import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;

import java.util.List;

public interface RetrieveAllExecutorsUseCase {
    // Return the list of all Executors
    List<Executor> getAllExecutors(); // Query
}
