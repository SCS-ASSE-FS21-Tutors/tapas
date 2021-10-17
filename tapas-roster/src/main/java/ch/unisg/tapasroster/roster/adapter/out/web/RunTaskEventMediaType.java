package ch.unisg.tapasroster.roster.adapter.out.web;

import ch.unisg.tapasroster.roster.domain.RunTaskEvent;
import org.json.JSONObject;

final public class RunTaskEventMediaType {
    public static final String MEDIA_TYPE = "application/json";

    public static String serialize(RunTaskEvent event) {
        JSONObject payload = new JSONObject();

        payload.put("taskId", event.getTaskId());
        payload.put("taskListName", event.getTaskListName());

        return payload.toString();
    }

    private RunTaskEventMediaType() { }
}
