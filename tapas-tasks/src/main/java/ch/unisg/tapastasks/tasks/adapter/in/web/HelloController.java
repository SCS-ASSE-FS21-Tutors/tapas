package ch.unisg.tapastasks.tasks.adapter.in.web;

import ch.unisg.tapastasks.tasks.domain.TaskList;
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
            "<h1>Task List Service</h1>" +
            "<ul>" +
            "<li><a href=\"v3/api-docs\" target=\"_blank\">API Docs</a></li>" +
            "<li><a href=\"swagger-ui.html\" target=\"_blank\">Swagger</a></li>" +
            "</ul>" +
            "</body></html>";
    }

    @RequestMapping("/tasklist")
    String taskListHtml() {
        // By navigating to this site, we can check all out tasks
        String taskListString = "";

        TaskList taskList = TaskList.getTapasTaskList();

        for (int i = 0; i < taskList.getListOfTasks().getValue().size(); i++) {
            taskListString += "<li>" + taskList.getListOfTasks().getValue().get(i).toString() + "</li>";
        }

        return "<html><body>" +
            "<h1>Task List</h1>" +
            "<ul>" + taskListString +
            "</ul>" +
            "</body></html>";


    }

}
