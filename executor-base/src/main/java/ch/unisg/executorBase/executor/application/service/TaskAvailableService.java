package ch.unisg.executorBase.executor.application.service;

import org.springframework.stereotype.Component;

import ch.unisg.executorBase.executor.application.port.in.TaskAvailableCommand;
import ch.unisg.executorBase.executor.application.port.in.TaskAvailableUseCase;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class TaskAvailableService implements TaskAvailableUseCase {

    @Override
    public void newTaskAvailable(TaskAvailableCommand command) {
      // Placeholder so spring can create a bean
    }
}
