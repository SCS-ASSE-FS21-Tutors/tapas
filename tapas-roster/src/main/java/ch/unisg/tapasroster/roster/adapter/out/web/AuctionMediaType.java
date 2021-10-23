package ch.unisg.tapasroster.roster.adapter.out.web;

import ch.unisg.tapasroster.roster.application.port.in.AssignTaskToExecutorCommand;
import ch.unisg.tapasroster.roster.domain.Task;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;

final public class AuctionMediaType {
    public static final String AUCTION_MEDIA_CONTENT_TYPE = "application/json";

    public static JSONObject toJSON(AssignTaskToExecutorCommand assignTaskToExecutorCommand) throws JSONException {
        JSONObject payload = new JSONObject();

        // TODO: Has to be changed to conform to uniform interface
        payload.put("taskUri", assignTaskToExecutorCommand.getTaskUri().getValue());
        payload.put("taskType", assignTaskToExecutorCommand.getTaskType().getValue());
        payload.put("deadline", LocalDateTime.now().plusDays(2).toString());

        return payload;
    }

    public static String serialize(AssignTaskToExecutorCommand assignTaskToExecutorCommand) throws JSONException {
        return toJSON(assignTaskToExecutorCommand).toString();
    }

    private AuctionMediaType() {
    }
}
