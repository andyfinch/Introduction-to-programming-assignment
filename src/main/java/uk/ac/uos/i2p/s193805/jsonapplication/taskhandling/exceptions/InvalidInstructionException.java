package uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.exceptions;

/**
 * Custom exception thrown when an invalid instrcution type os received
 */
public class InvalidInstructionException extends RuntimeException {

    /**
     * Instantiates a new Invalid instruction exception.
     *
     * @param message the message
     */
    public InvalidInstructionException(String message) {
        super(message);
    }
}
