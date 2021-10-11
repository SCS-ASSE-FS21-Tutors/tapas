package ch.unisg.executor1.executor.domain;

import java.util.concurrent.TimeUnit;

public class Executor extends ExecutorBase {
    
    private static final Executor executor = new Executor(ExecutorType.ADDITION);

    public static Executor getExecutor() {
        return executor;
    }

    private Executor(ExecutorType executorType) {
        super(executorType);
    }

    @Override
    String execution() {
        
        int a = 10;
        int b = 20;
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int result = a + b;

        return Integer.toString(result);
    }

}
