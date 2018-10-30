package uk.ac.uos.i2p.s193805.taskhandling.task;

import uk.ac.uos.i2p.s193805.taskhandling.instructionhandling.Concat;
import uk.ac.uos.i2p.s193805.taskhandling.instructionhandling.Instruction;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 26/10/2018
 * Time: 09:12
 */

public class ConcatTask<T extends String> extends Task<T> {

    @Override
    public void runInstruction() {
        Instruction<T> adder = new Concat<>();
        result = adder.runInstruction(paramList);

    }
}
