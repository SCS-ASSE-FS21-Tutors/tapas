package ch.unisg.tapasexecutorpool.pool.application.port.in;

import ch.unisg.tapasexecutorpool.pool.domain.Executor;

import java.util.Collection;

public interface ListExecutorsQuery {
    Collection<Executor> listExecutors();
}
