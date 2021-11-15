package ch.unisg.tapastasks.tasks.application.service;

import ch.unisg.tapascommon.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.application.port.in.RetrieveTaskFromTaskListQuery;
import ch.unisg.tapastasks.tasks.application.port.in.RetrieveTaskFromTaskListUseCase;
import ch.unisg.tapastasks.tasks.application.port.out.LoadTaskPort;
import ch.unisg.tapastasks.tasks.domain.TaskList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Transactional
public class RetrieveTaskFromTaskListService implements RetrieveTaskFromTaskListUseCase {

    private final LoadTaskPort loadTaskFromRepositoryPort;

    @Override
    public Optional<Task> retrieveTaskFromTaskList(RetrieveTaskFromTaskListQuery query) {
        TaskList taskList = TaskList.getTapasTaskList();

        Optional<Task> task = taskList.retrieveTaskById(query.getTaskId());

        Optional<Task> taskFromRepo = Optional.ofNullable(loadTaskFromRepositoryPort.loadTask(query.getTaskId(), taskList.getTaskListName()));

        return taskFromRepo;
    }
}
