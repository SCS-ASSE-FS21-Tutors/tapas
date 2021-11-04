package ch.unisg.tapasroster.roster.application.port.out;

import ch.unisg.tapasroster.roster.domain.Executor;

import java.util.List;
import java.util.Optional;

public interface QueryExecutorPoolExecutorsPort {
    Optional<List<Executor>> queryExecutorsFromExecutorPoolQuery();
}
