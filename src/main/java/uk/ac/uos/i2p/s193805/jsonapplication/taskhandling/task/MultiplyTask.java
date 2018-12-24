package uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.task;

import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.instructionhandling.Instruction;
import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.instructionhandling.Multiply;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 26/10/2018
 * Time: 09:12
 */

public class MultiplyTask extends Task {

    @Override
    public void runInstruction() {
        Instruction adder = new Multiply();
        result = adder.runInstruction(paramList);

    }
}
