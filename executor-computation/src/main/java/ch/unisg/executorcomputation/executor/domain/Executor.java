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

        String operator = "";
        if (inputData.contains("+")) {
            operator = "+";
        } else if (inputData.contains("-")) {
            operator = "-";
        } else if (inputData.contains("*")) {
            operator = "*";
        } else {
            return "invalid data";
        }

        double result = Double.NaN;

        // try {
        //     TimeUnit.SECONDS.sleep(5);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }

        if (operator.equalsIgnoreCase("+")) {
            String[] parts = inputData.split("\\+");
            double a = Double.parseDouble(parts[0]);
            double b = Double.parseDouble(parts[1]);
            result = a + b;
        } else if (operator.equalsIgnoreCase("*")) {
            String[] parts = inputData.split("\\*");
            double a = Double.parseDouble(parts[0]);
            double b = Double.parseDouble(parts[1]);
            result = a * b;
        } else if (operator.equalsIgnoreCase("-")) {
            String[] parts = inputData.split("-");
            double a = Double.parseDouble(parts[0]);
            double b = Double.parseDouble(parts[1]);
            result = a - b;
        }

        return Double.toString(result);
    }

}
