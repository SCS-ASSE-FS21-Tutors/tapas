package ch.unisg.tapas.tapasexecutor2.application;

import lombok.extern.log4j.Log4j2;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.EnvironmentAccess;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.PolyglotAccess;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Log4j2
@Component
public class ComputationService {

    public String executeComputation(String input) {

        try {

            OutputStream outputStream = new ByteArrayOutputStream();
            OutputStream logOutputStream = new ByteArrayOutputStream();

            Context context = Context.newBuilder("js")
                    .allowHostAccess(HostAccess.NONE)
                    .allowHostClassLookup(className -> false)
                    .allowCreateProcess(false)
                    .allowExperimentalOptions(false)
                    .allowAllAccess(false)
                    .allowEnvironmentAccess(EnvironmentAccess.NONE)
                    .allowPolyglotAccess(PolyglotAccess.NONE)
                    .allowIO(false)
                    .allowCreateThread(false)
                    .allowValueSharing(false)
                    .out(outputStream)
                    .logHandler(logOutputStream)
                    .build();


            ExecutorService executor = Executors.newSingleThreadExecutor();
            List<Future<Object>> results = executor.invokeAll(Arrays.asList(() -> context.eval("js", input)), 5, TimeUnit.SECONDS);
            Object result = results.get(0).get(10, TimeUnit.SECONDS);
            executor.shutdown();

            log.info("input: " + input + " => " + result.toString());

            return result.toString();

        } catch (Exception e) {

            log.warn("Exception while executing task with input: " + input);
            return "ERROR";
        }
    }
}
