package ch.unisg.executor1.executor.application.port.out;

public interface NotifyExecutorPoolPort {
    boolean notifyExecutorPool(String ip, int port, String executorType);
}
