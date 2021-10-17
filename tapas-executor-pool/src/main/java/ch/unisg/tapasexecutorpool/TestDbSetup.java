package ch.unisg.tapasexecutorpool;

import ch.unisg.tapasexecutorpool.pool.application.port.repository.ExecutorRepository;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Log
@Component
public class TestDbSetup implements CommandLineRunner {

    @Autowired
    ExecutorRepository repository;

    @Override
    public void run(String... args) {

        log.info("Setting up mock executors");

        repository.addExecutor(new Executor(
                new Executor.ExecutorName("Mock Executor"),
                new Executor.ExecutorType("string"), //We use 'string' because its the default executor type in the swagger ui
                new Executor.ExecutorUrl("doesnotexist")));
    }
}
