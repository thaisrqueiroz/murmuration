package project.murmuration.exceptions;

public class UniqueOfferAlreadySoldException extends RuntimeException {
    public UniqueOfferAlreadySoldException() {
        super("This unique offer has already been sold.");
    }
}
