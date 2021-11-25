package ch.unisg.tapas.example;

import ch.unisg.tapasbase.MicroserviceConfiguration;
import ch.unisg.tapasbase.TapasMicroservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(MicroserviceConfiguration.class)
@SpringBootApplication
public class CalculatorApplication {
    public static void main(String[] args) {

        SpringApplication CalculatorApp = new SpringApplication(CalculatorApplication.class);
        CalculatorApp.run(args);
    }

}
