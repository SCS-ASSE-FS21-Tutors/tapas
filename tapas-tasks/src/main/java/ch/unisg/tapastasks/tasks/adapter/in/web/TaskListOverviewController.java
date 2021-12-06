package ch.unisg.tapastasks.tasks.adapter.in.web;

import ch.unisg.tapastasks.tasks.application.port.out.LoadTaskListPort;
import ch.unisg.tapastasks.tasks.domain.TaskList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        TaskList taskList = loadTaskListPort.loadTaskList(new TaskList.TaskListName(taskListName));

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
