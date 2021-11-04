package ch.unisg.tapastasks.common;

import ch.unisg.tapastasks.tasks.domain.Task;
import org.springframework.beans.factory.annotation.Value;

public class Util {
    @Value("{$baseuri}")
    private static String baseUri;

    public static String buildTaskUri(String taskId){
        // Helper Method for consistent TaskUri across the tasklist
        return Util.baseUri + "/tasks/" + taskId;
    }
}
