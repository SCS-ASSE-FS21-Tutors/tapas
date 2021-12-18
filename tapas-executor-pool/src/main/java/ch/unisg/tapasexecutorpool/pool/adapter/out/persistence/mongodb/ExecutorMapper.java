package ch.unisg.tapasexecutorpool.pool.adapter.out.persistence.mongodb;

import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import org.springframework.stereotype.Component;

@Component
public class ExecutorMapper {

    Executor mapToDomainEntity(MongoExecutorDocument executorDocument) {
        return new Executor(
                new Executor.ExecutorId(executorDocument.executorId),
                new Executor.ExecutorName(executorDocument.executorName),
                new Executor.ExecutorType(executorDocument.executorType),
                new Executor.ExecutorUrl(executorDocument.executorUrl)
        );
    }

    MongoExecutorDocument mapToMongoDocument(Executor executor) {
        return new MongoExecutorDocument(
                executor.getExecutorId().getValue(),
                executor.getExecutorName().getValue(),
                executor.getExecutorType().getValue(),
                executor.getExecutorUrl().getValue()
        );
    }
}
