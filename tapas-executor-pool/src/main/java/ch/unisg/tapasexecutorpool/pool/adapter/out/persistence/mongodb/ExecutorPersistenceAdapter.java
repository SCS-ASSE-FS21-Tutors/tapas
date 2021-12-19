package ch.unisg.tapasexecutorpool.pool.adapter.out.persistence.mongodb;

import ch.unisg.tapasexecutorpool.pool.application.port.out.AddExecutorPort;
import ch.unisg.tapasexecutorpool.pool.application.port.out.LoadExecutorListPort;
import ch.unisg.tapasexecutorpool.pool.application.port.out.LoadExecutorPort;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class ExecutorPersistenceAdapter implements
        AddExecutorPort,
        LoadExecutorPort ,
        LoadExecutorListPort {

    private final ExecutorRepository executorRepository;

    private final ExecutorMapper executorMapper;

    @Override
    public void addExecutor(Executor executor) {
        log.info("Adding executor {} to database", executor.getExecutorName().getValue());
        MongoExecutorDocument mongoExecutorDocument = executorMapper.mapToMongoDocument(executor);
        executorRepository.save(mongoExecutorDocument);
    }

    @Override
    public Executor loadExecutor(Executor.ExecutorId executorId) {
        log.info("Loading executor {} from database", executorId.getValue());
        MongoExecutorDocument mongoExecutorDocument = executorRepository.findByExecutorId(executorId.getValue());
        Executor executor = executorMapper.mapToDomainEntity(mongoExecutorDocument);
        return executor;
    }

    @Override
    public List<Executor> loadExecutorList(){
        log.info("Loading executor list from database");
        List<MongoExecutorDocument> mongoExecutorList = executorRepository.findAll();
        List<Executor> executorList = new ArrayList<>();
        for (MongoExecutorDocument mongoExecutorDocument:mongoExecutorList) {
            Executor executor = executorMapper.mapToDomainEntity(mongoExecutorDocument);
            executorList.add(executor);
        }
        return executorList;
    }
}
