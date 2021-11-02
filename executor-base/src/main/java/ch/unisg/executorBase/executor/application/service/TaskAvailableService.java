package ch.unisg.executorbase.executor.application.service;

import org.springframework.stereotype.Component;

import ch.unisg.executorbase.executor.application.port.in.TaskAvailableCommand;
import ch.unisg.executorbase.executor.application.port.in.TaskAvailableUseCase;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class TaskAvailableService implements TaskAvailableUseCase {

    @Override
    public void newTaskAvailable(TaskAvailableCommand command) {
      // Placeholder so spring can create a bean, implementation of this function is inside the executors
    }
}
