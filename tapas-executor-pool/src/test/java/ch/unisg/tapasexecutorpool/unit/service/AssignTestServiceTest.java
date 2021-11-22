package ch.unisg.tapasexecutorpool.unit.service;

import ch.unisg.tapasexecutorpool.pool.application.port.out.SendTaskToExecutorPort;
import ch.unisg.tapasexecutorpool.pool.application.port.out.UpdateTaskStatusCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.out.UpdateTaskStatusCommandPort;
import ch.unisg.tapasexecutorpool.pool.application.port.repository.ExecutorRepository;
import ch.unisg.tapasexecutorpool.pool.application.service.AssignTaskService;
import ch.unisg.tapasexecutorpool.pool.application.service.NoExecutorFoundException;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import ch.unisg.tapasexecutorpool.pool.domain.Task;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AssignTestServiceTest {

    /**
     * Tests if a task is sent to the executor port if
     * the task service is called to assign a task
     * where a matching executor is in the repository
     */
    @Test
    public void testAssignNewTask(){

        // ARRANGE
        var mockRepo = mock(ExecutorRepository.class);
        var mockExecutorPort = mock(SendTaskToExecutorPort.class);
        var mockUpdateTaskStatusPort = mock(UpdateTaskStatusCommandPort.class);
        var service = new AssignTaskService(mockRepo, mockExecutorPort,mockUpdateTaskStatusPort);
        var executor = new Executor(
                new Executor.ExecutorName("SomeExecutor"),
                new Executor.ExecutorType("Sometype"),
                new Executor.ExecutorUrl("Someurl"));
        var newTask = new Task(
                new Task.TaskName("Somename"),
                new Task.TaskType("Sometype"));

        doReturn(Collections.singletonList(executor)).when(mockRepo).getExecutors();

        // ACT
        var canExecute = service.canExecute(newTask);
        service.enqueueTask(newTask);    // enqueues the task
        service.scheduleFixedRateTask(); // manually call scheduling routine

        // ASSERT
        assertTrue(canExecute);
        verify(mockExecutorPort, times(1)).sendTaskToExecutor(newTask, executor);
    }

    /**
     * Test what happens if a task is sent to the service
     * if there is no matching executor. In that case an exception should be thrown.
     */
    @Test
    public void testAssignNewTaskNoExecutorFound(){

        // ARRANGE
        var mockRepo = mock(ExecutorRepository.class);
        var mockExecutorPort = mock(SendTaskToExecutorPort.class);
        var mockUpdateTaskStatusPort = mock(UpdateTaskStatusCommandPort.class);
        var service = new AssignTaskService(mockRepo, mockExecutorPort,mockUpdateTaskStatusPort);
        var newTask = new Task(
                new Task.TaskName("Somename"),
                new Task.TaskType("Sometype"));

        doReturn(Collections.emptyList()).when(mockRepo).getExecutors();

        // ACT
        var canExecute = service.canExecute(newTask);
        assertThrows(NoExecutorFoundException.class, ()->service.enqueueTask(newTask)); // enqueues the task
        service.scheduleFixedRateTask(); // manually call scheduling routine

        // ASSERT
        assertFalse(canExecute);
        verify(mockExecutorPort, never()).sendTaskToExecutor(any(), any());
    }
}
