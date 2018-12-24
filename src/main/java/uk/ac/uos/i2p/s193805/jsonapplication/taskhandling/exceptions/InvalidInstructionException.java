package uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 24/12/2018
 * Time: 09:52
 */

public class InvalidInstructionException extends RuntimeException {

    public InvalidInstructionException(String message) {
        super(message);
    }
}
