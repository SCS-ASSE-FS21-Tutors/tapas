package ch.unisg.tapastasks.tasks.adapter.in.websockets;

import ch.unisg.tapastasks.tasks.adapter.in.formats.TaskJsonRepresentation;
import ch.unisg.tapastasks.tasks.application.handler.TasksChangedEvent;
import ch.unisg.tapastasks.tasks.application.port.out.LoadTaskListPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@Log4j2
@RestController
public class WebSocketController implements ApplicationListener<TasksChangedEvent> {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private LoadTaskListPort loadTaskListPort;

    private ObjectMapper om = new ObjectMapper();

    @GetMapping("/update")
    public void update() throws Exception{

        var tasks = loadTaskListPort.loadTaskList();
        var result = tasks.stream().map(TaskJsonRepresentation::new).collect(Collectors.toList());
        template.convertAndSend("/topic/taskList", om.writeValueAsString(result));
    }

    @Override
    public void onApplicationEvent(TasksChangedEvent tasksChangedEvent){

        try{
            var tasks = loadTaskListPort.loadTaskList();
            var result = tasks.stream().map(TaskJsonRepresentation::new).collect(Collectors.toList());
            template.convertAndSend("/topic/taskList", om.writeValueAsString(result));
        }catch (Exception ex){
            log.warn("Error while publishing tasks list via websockets");
        }

    }
}
