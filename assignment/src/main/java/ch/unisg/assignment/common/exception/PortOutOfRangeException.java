package ch.unisg.assignment.common.exception;

public class PortOutOfRangeException extends Exception {
    public PortOutOfRangeException() {
        super("Port is out of available range (1024-65535)");
    }
}
