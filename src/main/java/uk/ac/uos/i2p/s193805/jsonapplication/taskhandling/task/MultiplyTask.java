package uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.task;

import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.instructionhandling.Instruction;
import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.instructionhandling.Multiply;

/**
 * Implementation of an Multiply Task overriding runInstruction to process an Multiply instruction
 */
public class MultiplyTask extends Task {

    /**
     * Implementation of runInstruction to process Multiplying values
     */
    @Override
    public void runInstruction() {
        Instruction adder = new Multiply();
        result = adder.runInstruction(paramList);

    }
}
