package project.murmuration.exceptions;

public class SelfTransactionNotAllowedException extends RuntimeException {
    public SelfTransactionNotAllowedException() {
        super("You cannot make a transaction to yourself.");
    }
}