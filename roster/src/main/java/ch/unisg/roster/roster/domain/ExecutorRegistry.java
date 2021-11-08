package ch.unisg.roster.roster.domain;

import java.util.*;

import ch.unisg.common.valueobject.ExecutorURI;
import ch.unisg.roster.roster.domain.valueobject.ExecutorType;

/**
 * Registry that keeps a track of executors internal to the TAPAS application and the types of tasks
 * they can achieve. One executor may correspond to multiple task types.
 * This class is a singleton.
 */
public class ExecutorRegistry {
    private static ExecutorRegistry registry;

    private final Map<ExecutorType, Set<ExecutorURI>> executors;

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
     * @param executorIdentifier the identifier of the executor (can be any string)
     * @return true unless a runtime exception occurs
     */
    public boolean addExecutor(ExecutorType executorType, ExecutorURI executorURI) {
        Set<ExecutorURI> taskTypeExecs = executors.getOrDefault(executorType,
            Collections.synchronizedSet(new HashSet<>()));

        taskTypeExecs.add(executorURI);
        executors.put(executorType, taskTypeExecs);

        return true;
    }

    /**
     * Removes an executor from the registry. The executor is disassociated from all known task types.
     *
     * @param executorURI the identifier of the executor
     * @return true unless a runtime exception occurs
     */
    public boolean removeExecutor(ExecutorURI executorURI) {
        Iterator<ExecutorType> iterator = executors.keySet().iterator();

        while (iterator.hasNext()) {
            ExecutorType executorType = iterator.next();
            Set<ExecutorURI> set = executors.get(executorType);

            set.remove(executorURI);

            if (set.isEmpty()) {
                iterator.remove();
            }
        }

        return true;
    }

    /**
     * Checks if the registry contains an executor for a given task type. Used during task creation
     * to decide if a task can be executed.
     *
     * @param taskType the task type being auctioned
     * @return
     */
    public boolean containsTaskType(ExecutorType taskType) {
        return executors.containsKey(taskType);
    }

    /**
     * Adds a list of executors to current executor list. Should only be used on startup to
     * add all available executors from the executor pool to the registry.
     *
     * @param executors the initial executors
     * @return
     */
    public void init(Map<ExecutorType, Set<ExecutorURI>> executors) {
        this.executors.putAll(executors);
    }

}
