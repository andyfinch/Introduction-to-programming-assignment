package uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.instructionhandling;

import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.Result;

import java.util.List;

/**
 * Interface defining runInstrcution methods to operate on a supplied List
 */
public interface Instruction {

    /**
     * Run instruction result.
     *
     * @param values the values
     * @return the result
     * @throws IllegalArgumentException the illegal argument exception
     */
    Result runInstruction(List<String> values) throws IllegalArgumentException;

}
