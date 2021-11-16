package ch.unisg.executorcomputation.executor.domain;

import java.util.concurrent.TimeUnit;

import ch.unisg.executorbase.executor.domain.ExecutorBase;
import ch.unisg.executorbase.executor.domain.ExecutorType;

public class Executor extends ExecutorBase {

    private static final Executor executor = new Executor(ExecutorType.ADDITION);

    public static Executor getExecutor() {
        return executor;
    }

    private Executor(ExecutorType executorType) {
        super(executorType);
    }

    @Override
    protected
    String execution(String... input) {

        System.out.println(input);

        double result = Double.NaN;
        int a = Integer.parseInt(input[0]);
        int b = Integer.parseInt(input[2]);
        String operation = input[1];

        // try {
        //     TimeUnit.SECONDS.sleep(20);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }

        if (operation == "+") {
            result = a + b;
        } else if (operation == "*") {
            result = a * b;
        } else if (operation == "-") {
            result = a - b;
        }

        System.out.println("finish");

        return Double.toString(result);
    }

}
