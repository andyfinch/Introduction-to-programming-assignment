package uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.task;

import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.instructionhandling.Concat;
import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.instructionhandling.Instruction;

/**
 * Implementation of an Concat Task overriding runInstruction to process an Concat instruction
 */
public class ConcatTask extends Task {

    /**
     * Implementation of runInstruction to process Concatenating values
     */
    @Override
    public void runInstruction() {
        Instruction adder = new Concat();
        result = adder.runInstruction(paramList);

    }
}
