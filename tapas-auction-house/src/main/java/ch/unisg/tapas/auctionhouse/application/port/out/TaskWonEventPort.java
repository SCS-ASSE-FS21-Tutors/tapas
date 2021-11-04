package ch.unisg.tapas.auctionhouse.application.port.out;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.TaskJsonRepresentation;
import org.springframework.stereotype.Component;

//NOTE: This handles the case when we win an auction and need to place the task in our tasklist
public interface TaskWonEventPort {
    boolean addWonTaskToTaskList(TaskJsonRepresentation taskJsonRepresentation);
}
