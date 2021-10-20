package ch.unisg.tapasroster.roster.application.service;

import ch.unisg.tapascommon.tasks.domain.Task;
import ch.unisg.tapasroster.roster.application.port.in.UpdateTaskStateCommand;
import ch.unisg.tapasroster.roster.application.port.in.UpdateTaskStateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class UpdateTaskStateService implements UpdateTaskStateUseCase {
    @Override
    public Task updateTask(UpdateTaskStateCommand command) {
        return null;
    }
}
