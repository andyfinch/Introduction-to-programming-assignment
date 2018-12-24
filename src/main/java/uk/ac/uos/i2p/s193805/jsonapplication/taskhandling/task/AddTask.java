package uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.task;

import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.instructionhandling.Add;
import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.instructionhandling.Instruction;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 26/10/2018
 * Time: 09:12
 */

public class AddTask extends Task {

    @Override
    public void runInstruction() {
        Instruction adder = new Add();
        result = adder.runInstruction(paramList);

    }
}
