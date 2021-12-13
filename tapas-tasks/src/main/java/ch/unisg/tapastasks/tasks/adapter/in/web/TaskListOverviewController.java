package ch.unisg.tapastasks.tasks.adapter.in.web;

import ch.unisg.tapastasks.tasks.application.port.out.LoadTaskListPort;
import ch.unisg.tapastasks.tasks.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.List;

@RestController
public class TaskListOverviewController {

    @Value("${task.list.name}")
    private String taskListName;
    @Autowired
    private LoadTaskListPort loadTaskListPort;
    @Autowired
    private SpringTemplateEngine templateEngine;

    @GetMapping("/tasklist")
    String taskListHtml() {

        // By navigating to this site, we can check all out tasks
        List<Task> taskList = loadTaskListPort.loadTaskList();

        Context myContext = new Context();
        myContext.setVariable("taskList", taskList);

        String htmlTemplate = templateEngine.process("tasklist.html", myContext);
        return htmlTemplate;
    }

}
