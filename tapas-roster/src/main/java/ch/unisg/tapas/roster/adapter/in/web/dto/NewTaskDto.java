package ch.unisg.tapas.roster.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NewTaskDto {

    @JsonProperty("taskname")
    private String taskName;

    @JsonProperty("tasklist")
    private String taskListName;
}
