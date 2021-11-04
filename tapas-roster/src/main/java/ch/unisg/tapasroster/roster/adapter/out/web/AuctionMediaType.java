package ch.unisg.tapasroster.roster.adapter.out.web;

import ch.unisg.tapasroster.roster.application.port.in.AssignTaskToExecutorCommand;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.time.LocalDateTime;

final public class AuctionMediaType {
    public static final String AUCTION_MEDIA_CONTENT_TYPE = "application/json";

    public static JSONObject toJSON(AssignTaskToExecutorCommand assignTaskToExecutorCommand) throws JSONException {
        JSONObject payload = new JSONObject();

        payload.put("taskUri", assignTaskToExecutorCommand.getTaskUri().getValue());
        payload.put("taskType", assignTaskToExecutorCommand.getTaskType().getValue());
        String timestamp = Timestamp.valueOf(LocalDateTime.now().plusDays(2)).toString();
        payload.put("deadline", timestamp);

        return payload;
    }

    public static String serialize(AssignTaskToExecutorCommand assignTaskToExecutorCommand) throws JSONException {
        return toJSON(assignTaskToExecutorCommand).toString();
    }

    private AuctionMediaType() {
    }
}
