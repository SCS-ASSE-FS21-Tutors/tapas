package ch.unisg.tapasexecutorpool.pool.application.port.out;

import ch.unisg.tapasexecutorpool.pool.domain.Executor;

import java.util.List;

public interface LoadExecutorListPort {
    List<Executor> loadExecutorList();
}
