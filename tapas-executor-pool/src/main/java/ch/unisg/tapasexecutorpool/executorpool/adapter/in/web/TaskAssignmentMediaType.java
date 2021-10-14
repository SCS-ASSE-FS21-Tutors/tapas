package ch.unisg.tapasexecutorpool.executorpool.adapter.in.web;

import ch.unisg.tapasexecutorpool.executorpool.domain.TaskAssignmentReply;
import org.json.JSONObject;

final public class TaskAssignmentMediaType {
    public static final String MEDIA_TYPE = "application/json";

    public static String serialize(TaskAssignmentReply reply) {
        JSONObject payload = new JSONObject();

        payload.put("executorName", reply.getExecutorName());
        payload.put("assignmentType", reply.getAssignmentType());

        return payload.toString();
    }

    private TaskAssignmentMediaType() { }
}
