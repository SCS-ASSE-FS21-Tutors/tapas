package ch.unisg.tapasexecutorpool;

import ch.unisg.tapasexecutorpool.pool.application.port.repository.ExecutorRepository;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Log
@Component
public class TestDbSetup implements CommandLineRunner {

    @Autowired
    ExecutorRepository repository;

    @Autowired
    Environment env;

    @Override
    public void run(String... args) {

        var isLocal = Arrays.stream(env.getActiveProfiles()).anyMatch(p -> "local".equals(p));

        repository.addExecutor(new Executor(
                new Executor.ExecutorName("Executor 1 - Robot"),
                new Executor.ExecutorType("SMALLROBOT"),
                new Executor.ExecutorUrl(isLocal ? "http://localhost:8091" : "http://tapas-executor-1:8091")));

        repository.addExecutor(new Executor(
                new Executor.ExecutorName("Executor 2 - Calculation"),
                new Executor.ExecutorType("COMPUTATION"),
                new Executor.ExecutorUrl(isLocal ? "http://localhost:8092" : "http://tapas-executor-2:8092")));
    }
}
