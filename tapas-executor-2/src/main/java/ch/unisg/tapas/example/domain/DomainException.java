package ch.unisg.tapas.example.domain;

class DomainException extends RuntimeException{
    DomainException(final String message) {
        super(message);
    }
}
