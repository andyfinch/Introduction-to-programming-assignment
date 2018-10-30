package uk.ac.uos.i2p.s193805.taskhandling.task;

import uk.ac.uos.i2p.s193805.taskhandling.instructionhandling.Add;
import uk.ac.uos.i2p.s193805.taskhandling.instructionhandling.Instruction;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 26/10/2018
 * Time: 09:12
 */

public class AddTask<T extends Integer> extends Task<T> {

    @Override
    public void runInstruction() {
        Instruction<T> adder = new Add<>();
        result = adder.runInstruction(paramList);

    }
}
