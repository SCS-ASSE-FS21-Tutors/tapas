package ch.unisg.tapasexecutorpool.pool.application.service;

public class TaskNotBeingProcessedException extends RuntimeException{

    public TaskNotBeingProcessedException(String message) {
        super(message);
    }
}
