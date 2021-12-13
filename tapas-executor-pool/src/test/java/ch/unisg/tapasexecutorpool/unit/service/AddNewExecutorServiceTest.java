package ch.unisg.tapasexecutorpool.unit.service;

import ch.unisg.tapasexecutorpool.pool.application.port.in.AddNewExecutorToExecutorPoolCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.out.AddExecutorPort;
import ch.unisg.tapasexecutorpool.pool.application.port.repository.ExecutorRepository;
import ch.unisg.tapasexecutorpool.pool.application.service.AddNewExecutorService;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.*;

public class AddNewExecutorServiceTest {

    @Test
    public void testAddNewExecutor(){

        // ARRANGE
        var mockRepo = mock(ExecutorRepository.class);
        var mockPort = mock(AddExecutorPort.class);
        var service = new AddNewExecutorService(mockRepo, mockPort);
        var command = new AddNewExecutorToExecutorPoolCommand(
                new Executor.ExecutorName("Somename"),
                new Executor.ExecutorType("Sometype"),
                new Executor.ExecutorUrl("SomeUrl")
        );

        // ACT
        service.addNewExecutorToExecutorPool(command);

        // ASSERT
        verify(mockRepo, times(1)).addExecutor(notNull());
    }
}
