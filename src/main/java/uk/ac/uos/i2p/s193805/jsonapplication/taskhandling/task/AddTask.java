package uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.task;

import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.instructionhandling.Add;
import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.instructionhandling.Instruction;

/**
 * Implementation of an Add Task overriding runInstruction to process an Add instruction
 */
public class AddTask extends Task {

    /**
     * Implementation of runInstruction to process Adding values
     */
    @Override
    public void runInstruction() {
        Instruction adder = new Add();
        result = adder.runInstruction(paramList);

    }
}
