package ch.unisg.tapastasks.tasks.adapter.in.web;

import ch.unisg.tapastasks.tasks.application.port.out.LoadTaskListPort;
import ch.unisg.tapastasks.tasks.domain.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskListOverviewController {
    private final String taskListName;
    private final LoadTaskListPort loadTaskListPort;

    public TaskListOverviewController(@Value("${task.list.name}") String taskListName, LoadTaskListPort loadTaskListPort) {
        this.taskListName = taskListName;
        this.loadTaskListPort = loadTaskListPort;
    }

    @RequestMapping("/tasklist")
    String taskListHtml() {
        // By navigating to this site, we can check all out tasks
        String taskListString = "";

        List<Task> taskList = loadTaskListPort.loadTaskList();

        for (Task task: taskList) {
            taskListString += "<li>" + task.toString() + "</li>";
        }

        return "<html><body>" +
            "<h1>Task List "+taskListName +"</h1>" +
            "<ul>" + taskListString +
            "</ul>" +
            "</body></html>";
    }

}
