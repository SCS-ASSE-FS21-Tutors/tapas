package ch.unisg.tapas.example;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@Log
@Hidden
@RestController
public class HelloController {

    @RequestMapping("/")
    String index() {

        ExampleEnitity exampleEnitity = new ExampleEnitity("Foo", "Bar");
        exampleEnitity.setAge(5);

        log.info("Hello Request");
        return "<html><body>" +
                "<h1>Executor Pool</h1>" +
                "<ul>" +
                "<li><a href=\"v3/api-docs\" target=\"_blank\">API Docs</a></li>" +
                "<li><a href=\"swagger-ui.html\" target=\"_blank\">Swagger</a></li>" +
                "</ul>" +
                "</body></html>";
    }
    
}
