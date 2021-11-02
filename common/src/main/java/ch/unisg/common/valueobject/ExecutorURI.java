package ch.unisg.common.valueobject;

import ch.unisg.common.exception.InvalidExecutorURIException;
import lombok.Value;

@Value
public class ExecutorURI {
    private String value;

    public ExecutorURI(String uri) throws InvalidExecutorURIException {
        String ip = uri.split(":")[0];
        int port = Integer.parseInt(uri.split(":")[1]);
        // Check if valid ip4
        if (!ip.equalsIgnoreCase("localhost") &&
            !uri.matches("^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)(\\.(?!$)|$)){4}$")) {
            throw new InvalidExecutorURIException();
        // Check if valid port
        } else if (port < 1024 || port > 65535) {
            throw new InvalidExecutorURIException();
        }
        this.value = uri;
    }
}
