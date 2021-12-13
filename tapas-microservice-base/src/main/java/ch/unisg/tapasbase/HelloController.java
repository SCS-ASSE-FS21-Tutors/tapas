package ch.unisg.tapasbase;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Log
@Hidden
@RestController
public class HelloController {

    private String appName;
    private SpringTemplateEngine templateEngine;

    public HelloController(String appName, SpringTemplateEngine templateEngine) {
        this.appName = appName;
        this.templateEngine = templateEngine;
    }

    @RequestMapping("/")
    String index() {

        Context myContext = new Context();
        myContext.setVariable("appName", appName);

        String htmlTemplate = templateEngine.process("hello.html", myContext);
        return htmlTemplate;
    }
    
}
