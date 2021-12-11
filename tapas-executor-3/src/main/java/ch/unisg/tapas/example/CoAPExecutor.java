package ch.unisg.tapas.example;
import ch.unisg.tapasbase.TapasMicroservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@TapasMicroservice
@SpringBootApplication
public class CoAPExecutor {
    public static void main(String[] args) {

        SpringApplication CoAPExecutorApp = new SpringApplication(CoAPExecutor.class);
        CoAPExecutorApp.run(args);
    }

}
