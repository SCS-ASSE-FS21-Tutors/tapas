package ch.unisg.tapasexecutorpool.pool.application.service;

import ch.unisg.tapascommon.pool.adapter.in.formats.ExecutorJsonRepresentation;
import ch.unisg.tapasexecutorpool.pool.application.port.in.RetrieveExecutorFromPoolCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.RetrieveExecutorFromPoolUseCase;
import ch.unisg.tapasexecutorpool.pool.domain.ExecutorPool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Transactional
public class RetrieveExecutorFromPoolService implements RetrieveExecutorFromPoolUseCase {
    @Override
    public Optional<ExecutorJsonRepresentation> retrieveExecutorFromPool(RetrieveExecutorFromPoolCommand command) {
        var executorPool = ExecutorPool.getTapasExecutorPool();
        var executor = executorPool.retrieveExecutorById(command.getExecutorId());

        if (executor.isPresent()) {
            var representation = new ExecutorJsonRepresentation(executor.get());
            return Optional.of(representation);
        }

        return Optional.empty();
    }
}
