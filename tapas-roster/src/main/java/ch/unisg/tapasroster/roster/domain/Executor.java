package ch.unisg.tapasroster.roster.domain;


import lombok.Getter;
import lombok.Value;

import java.awt.*;
import java.util.UUID;

/**This is a domain entity**/
public class Executor {


    @Getter
    private final ExecutorUrl executorUrl;

    @Getter
    private final ExecutorType executorType;

    public Executor(ExecutorUrl executorUrl, ExecutorType executorType) {

        this.executorUrl =  executorUrl;
        this.executorType = executorType;

    }

    protected static Executor createExecutorWithUrlAndType(ExecutorUrl url, ExecutorType type) {
        //This is a simple debug message to see that the request has reached the right method in the core
        System.out.println("New Executor: " + url.getValue() + " " + type.getValue());
        return new Executor(url,type);
    }


    @Value
    public static class ExecutorUrl {
        String value;
    }

    @Value
    public static class ExecutorType {
        String value;
    }

}
