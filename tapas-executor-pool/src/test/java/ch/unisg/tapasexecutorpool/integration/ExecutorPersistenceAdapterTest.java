package ch.unisg.tapasexecutorpool.integration;

import ch.unisg.tapasbase.HelloController;
import ch.unisg.tapasexecutorpool.pool.adapter.out.persistence.mongodb.ExecutorMapper;
import ch.unisg.tapasexecutorpool.pool.adapter.out.persistence.mongodb.ExecutorPersistenceAdapter;
import ch.unisg.tapasexecutorpool.pool.adapter.out.persistence.mongodb.ExecutorRepository;
import ch.unisg.tapasexecutorpool.pool.adapter.out.persistence.mongodb.MongoExecutorDocument;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Import({ExecutorPersistenceAdapter.class, ExecutorMapper.class})
@DataMongoTest
public class ExecutorPersistenceAdapterTest {


    @Autowired
    private ExecutorRepository executorRepository;

    @Autowired
    private ExecutorPersistenceAdapter executorPersistenceAdapter;

    // Needed to load application context
    @MockBean
    private HelloController helloController;

    @AfterEach
    void clean() {
        executorRepository.deleteAll();
    }

    @Test
    void addExecutorTest(){
        // GIVEN
        String executorTestId = "executor-test-id";
        String executorTestName = "executor-test-name";
        String executorTestType = "executor-test-type";
        String executorTestUri = "executor-test-uri";

        Executor executor = new Executor(
                new Executor.ExecutorId(executorTestId),
                new Executor.ExecutorName(executorTestName),
                new Executor.ExecutorType(executorTestType),
                new Executor.ExecutorUrl(executorTestUri)
        );

        // WHEN
        executorPersistenceAdapter.addExecutor(executor);

        // THEN
        assertEquals(1, executorRepository.findAll().size(), "Number of executors in DB is not 1");
        assertEquals(executorTestName, executorRepository.findByExecutorId(executorTestId).getExecutorName(),
                "Executor name in DB is not correct");
    }

    @Test
    void loadExecutorTest(){
        // GIVEN
        String executorTestId = "executor-test-id";
        String executorTestName = "executor-test-name";
        String executorTestType = "executor-test-type";
        String executorTestUri = "executor-test-uri";

        MongoExecutorDocument mongoExecutorDocument = new MongoExecutorDocument(
                executorTestId,
                executorTestName,
                executorTestType,
                executorTestUri
        );
        executorRepository.save(mongoExecutorDocument);

        // WHEN
        Executor executor = executorPersistenceAdapter.loadExecutor(new Executor.ExecutorId(executorTestId));

        // THEN
        assertEquals(executorTestId, executor.getExecutorId().getValue(), "Loaded Executor ID is not correct");
        assertEquals(executorTestName, executor.getExecutorName().getValue(), "Loaded Executor Name is not correct");
        assertEquals(executorTestType, executor.getExecutorType().getValue(), "Loaded Executor Type is not correct");
        assertEquals(executorTestUri, executor.getExecutorUrl().getValue(), "Loaded Executor URI is not correct");
    }

    @Test
    void loadExecutorListTest(){
        // GIVEN

        int executorListSize = 5;

        List<MongoExecutorDocument> mongoExecutorDocuments = new ArrayList<>();

        String executorTestName = "executor-test-name";
        String executorTestType = "executor-test-type";
        String executorTestUri = "executor-test-uri";

        for(int i = 0; i<executorListSize;i++){
            MongoExecutorDocument mongoExecutorDocument = new MongoExecutorDocument(
                    UUID.randomUUID().toString(),
                    executorTestName,
                    executorTestType,
                    executorTestUri
            );
            mongoExecutorDocuments.add(mongoExecutorDocument);
            executorRepository.save(mongoExecutorDocument);
        }

        // WHEN
        List<Executor> executorList = executorPersistenceAdapter.loadExecutorList();

        // THEN
        assertEquals(executorListSize, executorList.size(), "Loaded Executor List size is not correct");
        assertTrue(executorList.stream().map(Executor::getExecutorId)
                .map(Executor.ExecutorId::getValue).collect(Collectors.toList())
                .equals(mongoExecutorDocuments.stream().map(MongoExecutorDocument::getExecutorId).collect(Collectors.toList())));
    }

}
