package ch.unisg.common.valueobject;

import java.net.URI;
import lombok.Value;

@Value
public class ExecutorURI {
    private URI value;

    public ExecutorURI(String uri) {
        this.value = URI.create(uri);
    }
}
