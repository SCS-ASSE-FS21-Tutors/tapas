package ch.unisg.tapas.auctionhouse.domain;

import lombok.Value;

import java.net.URI;
import java.util.*;

/**
 * Registry that keeps a track of executors internal to the TAPAS application and the types of tasks
 * they can achieve. One executor may correspond to multiple task types. This mapping is used when
 * bidding for tasks: the auction house will only bid for tasks for which there is a known executor.
 * This class is a singleton.
 */
public class ExecutorRegistry {
    private static ExecutorRegistry registry;

    private final Map<Auction.AuctionedTaskType, Set<ExecutorUri>> executors;

    private ExecutorRegistry() {
        this.executors = new Hashtable<>();
    }

    public static synchronized ExecutorRegistry getInstance() {
        if (registry == null) {
            registry = new ExecutorRegistry();
        }

        return registry;
    }

    /**
     * Adds an executor to the registry for a given task type.
     *
     * @param taskType the type of the task
     * @param executorUri the executor's URI
     * @return true unless a runtime exception occurs
     */
    public boolean addExecutor(Auction.AuctionedTaskType taskType, ExecutorUri executorUri) {
        Set<ExecutorUri> taskTypeExecs = executors.getOrDefault(taskType,
            Collections.synchronizedSet(new HashSet<>()));

        taskTypeExecs.add(executorUri);
        executors.put(taskType, taskTypeExecs);

        return true;
    }

    /**
     * Removes an executor from the registry. The executor is disassociated from all known task types.
     *
     * @param executorUri the executor's URI
     * @return true unless a runtime exception occurs
     */
    public boolean removeExecutor(ExecutorUri executorUri) {
        Iterator<Auction.AuctionedTaskType> iterator = executors.keySet().iterator();

        while (iterator.hasNext()) {
            Auction.AuctionedTaskType taskType = iterator.next();
            Set<ExecutorUri> set = executors.get(taskType);

            set.remove(executorUri);

            if (set.isEmpty()) {
                iterator.remove();
            }
        }

        return true;
    }

    /**
     * Checks if the registry contains an executor for a given task type. Used during an auction to
     * decide if a bid should be placed.
     *
     * @param taskType the task type being auctioned
     * @return
     */
    public boolean containsTaskType(Auction.AuctionedTaskType taskType) {
        return executors.containsKey(taskType);
    }

    // Value Object for the executor identifier
    @Value
    public static class ExecutorUri {
        URI value;
    }
}
