package ch.unisg.tapasexecutors.executors.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


@Component
public class DigitalExecutor {
    public enum State {
        IDLE, BUSY
    }

    private final static String WORDS_BASE_API = "https://random-word-api.herokuapp.com/word?number=";
    private final static String[] VERBS = new String[] {"hits", "exaggerates", "boasts", "scurries", "warps", "saunters", "flutters"};
    private final static String[] ARTICLES = new String[] {"on a", "with a", "above a", "with the", "under a"};

    @Getter
    private final ExecutorName executorName = new ExecutorName("RandomWordExecutor");

    @Getter
    @Setter
    private ExecutorState executorState;

    @Getter
    private ExecutorId executorId;

    public DigitalExecutor() {
        this.executorState = new ExecutorState(State.IDLE);
        this.executorId = new DigitalExecutor.ExecutorId(UUID.randomUUID().toString());
    }

    private static String constructSentence() {
        int nWords = 3;
        Random rand = new Random();
        String[] randomWordList = getRandomWordsArray(nWords);
        return randomWordList[0] + VERBS[rand.nextInt(VERBS.length)] + // objective with verb
                randomWordList[1] + ARTICLES[rand.nextInt(ARTICLES.length)] + // second word with article
                randomWordList[2];
    }

    private static String[] getRandomWordsArray(int nWords) {
        String randomWordsString = getRandomWordsFromAPI(nWords);
        if (!randomWordsString.equals("")) {
            randomWordsString = randomWordsString.replace("[", "");
            randomWordsString = randomWordsString.replace("]", "");
            randomWordsString = randomWordsString.replace("\"", "");
            return randomWordsString.split(",");
        } else return new String[] {""};
    }

    private static String getRandomWordsFromAPI(int nWords) {
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

    public String runTask() {
        executorState = new ExecutorState(State.BUSY);
        String sentence = constructSentence();
        executorState = new ExecutorState(State.IDLE);

        return sentence;
    }

    @Value
    static class ExecutorId {
        String value;
    }

    @Value
    static class ExecutorName {
        String value;
    }

    @Value
    static class ExecutorState {
        State value;
    }

    @Value
    static class ExecutorType {
        String value;
    }
}
