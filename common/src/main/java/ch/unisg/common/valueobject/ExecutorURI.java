package ch.unisg.common.valueobject;

import ch.unisg.common.exception.InvalidExecutorURIException;
import lombok.Value;

@Value
public class ExecutorURI {
    private String value;

    public ExecutorURI(String uri) throws InvalidExecutorURIException {
        if (uri.equalsIgnoreCase("localhost") ||
            uri.matches("^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)(\\.(?!$)|$)){4}$")) {
            this.value = uri;
        } else {
            throw new InvalidExecutorURIException();
        }
    }
}
