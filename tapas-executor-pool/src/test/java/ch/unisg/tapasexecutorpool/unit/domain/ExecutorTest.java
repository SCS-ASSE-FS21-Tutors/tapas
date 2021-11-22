package ch.unisg.tapasexecutorpool.unit.domain;

import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExecutorTest {

    /**
     * Tests that if a two executor entities are created
     * that they have different ids.
     */
    @Test
    public void testExecutorNewId(){

        // ACT
        Executor executor1 = new Executor(
                new Executor.ExecutorName("name"),
                new Executor.ExecutorType("type"),
                new Executor.ExecutorUrl("Url"));

        Executor executor2 = new Executor(
                new Executor.ExecutorName("name"),
                new Executor.ExecutorType("type"),
                new Executor.ExecutorUrl("Url"));

        // ASSERT
        assertNotEquals(executor1.getExecutorId(), executor2.getExecutorId());
        assertEquals(executor1.getExecutorType(), executor2.getExecutorType());
    }
}
