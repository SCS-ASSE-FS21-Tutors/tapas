package ch.unisg.assignment.assignment.domain.valueobject;

import ch.unisg.assignment.common.exception.InvalidIP4Exception;
import lombok.Value;

@Value
public class IP4Adress {
    private String value;

    public IP4Adress(String ip4) throws InvalidIP4Exception {
        if (ip4.equalsIgnoreCase("localhost") ||
                ip4.matches("^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)(\\.(?!$)|$)){4}$")) {
            this.value = ip4;
        } else {
            throw new InvalidIP4Exception();
        }
    }
}





