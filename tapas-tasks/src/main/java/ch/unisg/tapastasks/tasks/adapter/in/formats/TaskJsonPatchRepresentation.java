package ch.unisg.tapastasks.tasks.adapter.in.formats;

import ch.unisg.tapastasks.tasks.domain.Task;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TaskJsonPatchRepresentation {
    private final JsonNode patch;

    public TaskJsonPatchRepresentation(JsonNode patch) {
        this.patch = patch;
    }

    public Optional<Task.Status> extractFirstTaskStatusChange() {
        Optional<JsonNode> status = extractFirst(node ->
            isPatchReplaceOperation(node) && hasPath(node, "/taskStatus")
        );

        if (status.isPresent()) {
            String taskStatus = status.get().get("value").asText();
            return Optional.of(Task.Status.valueOf(taskStatus));
        }

        return Optional.empty();
    }

    public Optional<Task.ServiceProvider> extractFirstServiceProviderChange() {
        Optional<JsonNode> serviceProvider = extractFirst(node ->
                (isPatchReplaceOperation(node) || isPatchAddOperation(node))
                && hasPath(node, "/serviceProvider")
        );

        return (serviceProvider.isEmpty()) ? Optional.empty()
            : Optional.of(new Task.ServiceProvider(serviceProvider.get().get("value").asText()));
    }

    public Optional<Task.OutputData> extractFirstOutputDataAddition() {
        Optional<JsonNode> output = extractFirst(node ->
            isPatchAddOperation(node) && hasPath(node, "/outputData")
        );

        return (output.isEmpty()) ? Optional.empty()
            : Optional.of(new Task.OutputData(output.get().get("value").asText()));
    }

    private Optional<JsonNode> extractFirst(Predicate<? super JsonNode> predicate) {
        Stream<JsonNode> stream = StreamSupport.stream(patch.spliterator(), false);
        return stream.filter(predicate).findFirst();
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
