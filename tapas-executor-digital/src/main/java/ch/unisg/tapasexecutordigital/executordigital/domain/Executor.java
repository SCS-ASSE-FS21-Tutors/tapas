package ch.unisg.tapasexecutordigital.executordigital.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.apache.tomcat.jni.Time;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**This is a domain entity**/
public class Executor {
    public enum State {
        IDLE, RUNNING
    }

    private final static String WORDS_BASE_API = "https://random-word-api.herokuapp.com/word?number=";
    private final static String[] VERBS = new String[] {"hits", "exaggerates", "boasts", "scurries", "warps", "saunters", "flutters"};
    private final static String[] ARTICLES = new String[] {"on a", "with a", "above a", "with the", "under a"};

    @Getter
    private final ExecutorName executorName = new ExecutorName("RandomWordExecutor");

    @Getter
    private final TaskType taskType = new TaskType("DigitalTask");

    @Getter
    @Setter
    private ExecutorState executorState;

    @Getter
    private Task assignedTask;

    // Singleton pattern
    private static final Executor executor = new Executor();

    public Executor() {
        executorState = new ExecutorState(State.IDLE);
    }

    public static Executor getExecutor() {
        return executor;
    }

    public void assignTask(Task task) {
        assignedTask = task;
        task.setTaskState(new Task.TaskState(Task.State.ASSIGNED));
    }

    public void startTask() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            // silence
        }
        constructSentence();
        executorState = new ExecutorState(State.IDLE);
        // TODO: what happens when the Executor is done?
    }

    private String constructSentence() {
        int nWords = 3;
        Random rand = new Random();
        String[] randomWordList = getRandomWordsArray(nWords);
        StringBuilder sb = new StringBuilder();
        sb.append(randomWordList[0] + VERBS[rand.nextInt(VERBS.length)]); // objective with verb
        sb.append(randomWordList[1] + ARTICLES[rand.nextInt(ARTICLES.length)]); // second word with article
        sb.append(randomWordList[2]); // subjective
        return sb.toString();
    }

    private String[] getRandomWordsArray(int nWords) {
        String randomWordsString = getRandomWordsFromAPI(nWords);
        if (!randomWordsString.equals("")) {
            randomWordsString = randomWordsString.replace("[", "");
            randomWordsString = randomWordsString.replace("]", "");
            randomWordsString = randomWordsString.replace("\"", "");
            return randomWordsString.split(",");
        } else return new String[] {""};
    }

    private String getRandomWordsFromAPI(int nWords) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(WORDS_BASE_API + nWords))
                .header("Accept", "application/json")
                .build();

        CompletableFuture<String> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
        String responseContent = "";
        try {
            responseContent = response.get(1, TimeUnit.SECONDS);
        } catch (ExecutionException | TimeoutException | InterruptedException e) {
            responseContent = "";
        }
        return responseContent;
    }

    @Value
    public static class ExecutorId {
        private String value;
    }

    @Value
    public static class ExecutorName {
        private String value;
    }

    @Value
    public static class ExecutorState {
        private State value;
    }

    @Value
    public static class TaskType {
        private String value;
    }
}
