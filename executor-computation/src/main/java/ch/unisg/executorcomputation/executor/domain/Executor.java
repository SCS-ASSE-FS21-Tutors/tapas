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
    String execution(String inputData) {

        System.out.println(inputData);

        String operator = "";
        if (inputData.contains("+")) {
            operator = "+";
        } else if (inputData.contains("-")) {
            operator = "-";
        } else if (inputData.contains("*")) {
            operator = "*";
        }

        // System.out.println(operator);

        // double result = Double.NaN;

        // System.out.print(inputData.split("+"));

        // int a = Integer.parseInt(inputData.split(operator)[0]);
        // int b = Integer.parseInt(inputData.split(operator)[1]);

        // // try {
        // //     TimeUnit.SECONDS.sleep(20);
        // // } catch (InterruptedException e) {
        // //     e.printStackTrace();
        // // }

        // if (operator.equalsIgnoreCase("+")) {
        //     result = a + b;
        // } else if (operator.equalsIgnoreCase("*")) {
        //     result = a * b;
        // } else if (operator.equalsIgnoreCase("-")) {
        //     result = a - b;
        // }

        // System.out.println("Result: " + result);

        double result = 0.0;

        return Double.toString(result);
    }

}
