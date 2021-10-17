package ch.unisg.tapasexecutorpool.pool.adapter.in.web;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log
@Hidden
@RestController
public class HelloController {

    @RequestMapping("/")
    String index() {

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
