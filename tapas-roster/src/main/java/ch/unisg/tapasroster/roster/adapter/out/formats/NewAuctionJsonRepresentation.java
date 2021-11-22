package ch.unisg.tapasroster.roster.adapter.out.formats;

import ch.unisg.tapascommon.communication.ServiceHostAddresses;
import ch.unisg.tapascommon.tasks.domain.Task;

public class NewAuctionJsonRepresentation {
    public static final String MEDIA_TYPE = "application/auction+json";

    public static String serialize(Task task, long deadline) {

        // {
        //  "taskUri" : "http://example.org",
        //  "taskType" : "taskType1",
        //  "deadline" : 10000
        //}

        return "{\n" +
                "  \"taskUri\": \"" +
                ServiceHostAddresses.getTaskServiceHostAddress() + task.getTaskId().getValue() +
                "\"," +
                "  \"taskType\": \"" +
                task.getTaskType().getValue().name() +
                "\"," +
                "  \"deadline\": \"" +
                deadline +
                "\"\n" +
                "}";
    }
}
