package ch.unisg.tapasroster.roster.application.port.out;

import ch.unisg.tapasroster.roster.domain.ForwardTaskToPoolEvent;

public interface ExecuteTaskOnPoolEventPort {
    void forwardTaskToPoolEvent(ForwardTaskToPoolEvent event);
}
