package ch.unisg.tapasexecutorpool.executorpool.application.port.in;

import java.util.List;

public interface RetrieveAllExecutorIdsUseCase {
    // Return a list of the IDs of all Executors
    List<Integer> getAllExecutorIDs(); // Query therefore no command parameter
}
