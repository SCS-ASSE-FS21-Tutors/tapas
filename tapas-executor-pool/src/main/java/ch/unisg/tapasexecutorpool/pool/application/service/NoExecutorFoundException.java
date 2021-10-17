package ch.unisg.tapasexecutorpool.pool.application.service;

public class NoExecutorFoundException extends RuntimeException{

    public NoExecutorFoundException(String message) {
        super(message);
    }
}
