package ch.unisg.assignment.assignment.domain.valueobject;

import ch.unisg.assignment.common.exception.PortOutOfRangeException;
import lombok.Value;

@Value
public class Port {
    private int value;

    public Port(int port) throws PortOutOfRangeException {
        if (1024 <= port && port <= 65535) {
            this.value = port;
        } else {
            throw new PortOutOfRangeException();
        }
    }
}
