package ch.unisg.tapastasks.tasks.adapter.in.formats;

import ch.unisg.tapastasks.tasks.domain.Task;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TaskJsonPatchRepresentation {
    private final JsonNode patch;

    public TaskJsonPatchRepresentation(JsonNode patch) {
        this.patch = patch;
    }

    public Optional<Task.ServiceProvider> extractServiceProvider() {
        Stream<JsonNode> stream = StreamSupport.stream(patch.spliterator(), false);

        Optional<JsonNode> output = stream
            .filter(node -> (isPatchReplaceOperation(node) || isPatchAddOperation(node))
                && hasPath(node, "/serviceProvider"))
            .findFirst();

        return (output.isEmpty()) ? Optional.empty()
            : Optional.of(new Task.ServiceProvider(output.get().get("value").asText()));
    }

    public Optional<Task.OutputData> extractOutput() {
        Stream<JsonNode> stream = StreamSupport.stream(patch.spliterator(), false);

        Optional<JsonNode> output = stream
            .filter(node -> isPatchAddOperation(node) && hasPath(node, "/outputData"))
            .findFirst();

        return (output.isEmpty()) ? Optional.empty()
            : Optional.of(new Task.OutputData(output.get().get("value").asText()));
    }

    private boolean isPatchAddOperation(JsonNode node) {
        return isPatchOperationOfType(node, "add");
    }

    private boolean isPatchReplaceOperation(JsonNode node) {
        return isPatchOperationOfType(node, "replace");
    }

    private boolean isPatchOperationOfType(JsonNode node, String operation) {
        return node.isObject() && node.get("op") != null
            && node.get("op").asText().equalsIgnoreCase(operation);
    }

    private boolean hasPath(JsonNode node, String path) {
        return node.isObject() && node.get("path") != null
            && node.get("path").asText().equalsIgnoreCase(path);
    }
}
