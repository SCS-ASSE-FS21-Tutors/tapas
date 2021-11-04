package ch.unisg.tapasroster.roster.application.service;

import ch.unisg.tapasroster.roster.application.port.in.AssignTaskToExecutorCommand;
import ch.unisg.tapasroster.roster.application.port.in.AssignTaskToExecutorUseCase;
import ch.unisg.tapasroster.roster.application.port.out.QueryExecutorPoolExecutorsPort;
import ch.unisg.tapasroster.roster.application.port.out.TaskAssignedInternallyEventPort;
import ch.unisg.tapasroster.roster.application.port.out.TriggerAuctionForTaskEventPort;
import ch.unisg.tapasroster.roster.domain.Executor;
import ch.unisg.tapasroster.roster.domain.Task;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class AssignTaskToExecutorService implements AssignTaskToExecutorUseCase {

    private final QueryExecutorPoolExecutorsPort queryExecutorPoolExecutorsPort;
    private final TaskAssignedInternallyEventPort taskAssignedInternallyEventPort;
    private final TriggerAuctionForTaskEventPort triggerAuctionForTaskEventPort;

    public AssignTaskToExecutorService(
            QueryExecutorPoolExecutorsPort queryExecutorPoolExecutorsPort,
            TaskAssignedInternallyEventPort taskAssignedInternallyEventPort,
            TriggerAuctionForTaskEventPort triggerAuctionForTaskEventPort
    ) {
        this.queryExecutorPoolExecutorsPort = queryExecutorPoolExecutorsPort;
        this.taskAssignedInternallyEventPort = taskAssignedInternallyEventPort;
        this.triggerAuctionForTaskEventPort = triggerAuctionForTaskEventPort;
    }

    @Override
    public boolean assignTaskToExecutor(AssignTaskToExecutorCommand command) {
        boolean assigned = false;
        // IN:Task from TaskList
        Task task  = new Task(
                command.getTaskId(),
                command.getTaskType(),
                command.getTaskUri()
        );
        String typeOfNewTask = command.getTaskType().getValue();

        // get executors via port
        Optional<List<Executor>> parsedExecutorList = queryExecutorPoolExecutorsPort.queryExecutorsFromExecutorPoolQuery();
        // find matching type (task==executor)
        if (parsedExecutorList.isPresent()){
            for (Executor executor : parsedExecutorList.get()) {
                String executorType = executor.getExecutorType().getValue();
                String executorUrl = executor.getExecutorUrl().getValue();
                if (executorType.equals(typeOfNewTask)) {
                    // return that assignment was successful
                    assigned =  taskAssignedInternallyEventPort.publishTaskAssignedInternallyEvent(executorUrl);
                }
            }
        }
        if (!assigned){
            assigned = triggerAuctionForTaskEventPort.triggerAuctionForTask(command);
        }
        // Start an auction since an executor could not be found in Executor Pool
        //OUT: Later will be current status of the TaskList
        return assigned;
    }
}