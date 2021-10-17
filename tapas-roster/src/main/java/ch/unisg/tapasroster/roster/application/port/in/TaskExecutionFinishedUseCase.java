package ch.unisg.tapasroster.roster.application.port.in;

import ch.unisg.tapasroster.roster.domain.TaskFinishedReply;

import java.util.Optional;

public interface TaskExecutionFinishedUseCase {
    void notifyRosterTaskExecutionFinished(TaskExecutionFinishedCommand command);
}
