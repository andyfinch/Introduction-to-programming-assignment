package uk.ac.uos.i2p.s193805.parser.exceptions;

/**
 * Custom exception used when invalid JSON is parsed
 */
public class JsonParseException extends RuntimeException {

    /**
     * Instantiates a new Json parse exception.
     *
     * @param message the message
     */
    public JsonParseException(String message) {
        super(message);
    }


}
