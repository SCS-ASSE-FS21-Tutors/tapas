package ch.unisg.tapas.tapasexecutor2;

import ch.unisg.tapasbase.MicroserviceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@Import(MicroserviceConfiguration.class)
@EnableAsync
@SpringBootApplication
public class CalculatorApplication {
    public static void main(String[] args) {

        SpringApplication CalculatorApp = new SpringApplication(CalculatorApplication.class);
        CalculatorApp.run(args);
    }

}
