package uk.ac.uos.i2p.s193805.taskhandling.instructionhandling;

import uk.ac.uos.i2p.s193805.taskhandling.Result;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 25/10/2018
 * Time: 09:40
 */

public interface Instruction {

    Result runInstruction(String...params);
}
