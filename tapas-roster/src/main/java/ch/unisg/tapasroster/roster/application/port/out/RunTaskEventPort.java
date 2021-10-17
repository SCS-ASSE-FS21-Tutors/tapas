package ch.unisg.tapasroster.roster.application.port.out;

import ch.unisg.tapasroster.roster.domain.RunTaskEvent;

public interface RunTaskEventPort {

    void runTaskEvent(RunTaskEvent event);
}
