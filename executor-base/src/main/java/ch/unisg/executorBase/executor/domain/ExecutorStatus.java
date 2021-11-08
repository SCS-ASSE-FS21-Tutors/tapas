package ch.unisg.executorbase.executor.domain;

public enum ExecutorStatus {
    STARTING_UP, // Executor is starting
    EXECUTING, // Executor is currently executing a task
    IDLING, // Executor has no tasks left and is waiting for new instructions
}
