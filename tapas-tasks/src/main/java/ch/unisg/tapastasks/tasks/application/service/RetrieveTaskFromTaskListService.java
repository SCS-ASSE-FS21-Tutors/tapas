package ch.unisg.tapastasks.tasks.application.service;

import ch.unisg.tapastasks.tasks.application.port.in.RetrieveTaskFromTaskListCommand;
import ch.unisg.tapastasks.tasks.application.port.in.RetrieveTaskFromTaskListUseCase;
import ch.unisg.tapastasks.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.TaskList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Transactional
public class RetrieveTaskFromTaskListService implements RetrieveTaskFromTaskListUseCase {
    @Override
    public Optional<Task> retrieveTaskFromTaskList(RetrieveTaskFromTaskListCommand command) {
        TaskList taskList = TaskList.getTapasTaskList();
        return taskList.retrieveTaskById(command.getTaskId());
    }
}
