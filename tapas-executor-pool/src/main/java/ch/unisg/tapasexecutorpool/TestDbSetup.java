package ch.unisg.tapasexecutorpool;

import ch.unisg.tapasexecutorpool.pool.application.port.out.LoadExecutorListPort;
import ch.unisg.tapasexecutorpool.pool.application.port.repository.ExecutorRepository;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

@Log
@Component
public class TestDbSetup implements CommandLineRunner {

    @Autowired
    ExecutorRepository repository;

    @Autowired
    Environment env;

    @Autowired
    LoadExecutorListPort loadExecutorListPort;

    @Override
    public void run(String... args) {

        List<Executor> executorList = loadExecutorListPort.loadExecutorList();

        for(Executor executor: executorList){
            log.info("Retrieved persistent executor from DB: " + executor.getExecutorName().getValue());
            repository.addExecutor(executor);
        }
    }
}
