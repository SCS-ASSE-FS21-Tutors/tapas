package ch.unisg.tapasroster.roster.application.port.out;

import ch.unisg.tapascommon.pool.domain.Executor;

import java.util.List;

public interface QueryAvailableExecutorsFromPoolEventHandler {
    boolean retrieveAvailableExecutors(QueryAvailableExecutorsFromPoolEvent event);
}
