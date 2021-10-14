package ch.unisg.tapasexecutorpool.pool.domain;

import lombok.Getter;
import lombok.Value;

import javax.swing.text.html.Option;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


/**This is our aggregate root**/
public class ExecutorPool {

    @Getter
    private final ListOfExecutors listOfExecutors;

    private static final ExecutorPool executorPool = new ExecutorPool();

    private ExecutorPool() {
        this.listOfExecutors = new ListOfExecutors(new LinkedList<Executor>());
    }

    public static ExecutorPool getExecutorPool() {
        return executorPool;
    }

    public Executor addNewExecutor(Executor.ExecutorName name, Executor.ExecutorType type, Executor.ExecutorPort port) {
        Executor newExecutor = Executor.createExecutor(name,type, port);
        listOfExecutors.value.add(newExecutor);
        //This is a simple debug message to see that the executorPool list is growing with each new request
        System.out.println("Number of Executors: "+listOfExecutors.value.size());
        //Here we would also publish a domain event to other entities in the core interested in this event.
        //However, we skip this here as it makes the core even more complex (e.g., we have to implement a light-weight
        //domain event publisher and subscribers (see "Implementing Domain-Driven Design by V. Vernon, pp. 296ff).
        return newExecutor;
    }

    public Optional<Executor> retrieveExecutorById(Executor.ExecutorId id) {
        for (Executor executor : listOfExecutors.value) {
            if (executor.getExecutorId().getValue().equalsIgnoreCase(id.getValue())) {
                return Optional.of(executor);
            }
        }

        return Optional.empty();
    }

    @Value
    public static class ListOfExecutors {
        private List<Executor> value;
    }

}
