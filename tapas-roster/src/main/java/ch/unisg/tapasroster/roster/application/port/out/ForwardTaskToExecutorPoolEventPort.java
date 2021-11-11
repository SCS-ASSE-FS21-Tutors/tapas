package ch.unisg.tapasroster.roster.application.port.out;

import ch.unisg.tapasroster.roster.domain.ForwardTaskToExecutorPoolEvent;

public interface ForwardTaskToExecutorPoolEventPort {
    void forwardTaskToExecutorPoolEvent(ForwardTaskToExecutorPoolEvent event);
}
