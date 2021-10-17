package ch.unisg.tapas.example;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Executor-2-API", version = "1"))
public class CalculatorApplication {
    public static void main(String[] args) {

        SpringApplication CalculatorApp = new SpringApplication(CalculatorApplication.class);
        CalculatorApp.run(args);
    }

}
