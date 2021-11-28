package ch.unisg.tapasbase;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@Log
@Hidden
@RestController
public class HelloController {

    private String appName;

    public HelloController(@Value("${spring.application.name:Please set spring.application.name}") String appName) {
        this.appName = appName;
    }

    @RequestMapping("/")
    String index() {

        log.info("Hello Request");
        return "<html><body>" +
                "<h1>"+appName+" Group 3</h1>" +
                "<ul>" +
                "<li><a href=\"v3/api-docs\" target=\"_blank\">API Docs</a></li>" +
                "<li><a href=\"swagger-ui.html\" target=\"_blank\">Swagger</a></li>" +
                "</ul>" +
                "</body></html>";
    }
    
}
