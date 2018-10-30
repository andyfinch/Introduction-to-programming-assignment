package uk.ac.uos.i2p.s193805.taskhandling.task;

import uk.ac.uos.i2p.s193805.taskhandling.instructionhandling.Instruction;
import uk.ac.uos.i2p.s193805.taskhandling.instructionhandling.Multiply;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 26/10/2018
 * Time: 09:12
 */

public class MultiplyTask<T extends Integer> extends Task<T> {

    @Override
    public void runInstruction() {
        Instruction<T> adder = new Multiply<>();
        result = adder.runInstruction(paramList);

    }
}
