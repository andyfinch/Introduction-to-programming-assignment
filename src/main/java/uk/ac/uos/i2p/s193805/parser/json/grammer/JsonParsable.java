package uk.ac.uos.i2p.s193805.parser.json.grammer;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 28/12/2018
 * Time: 16:42
 */

public interface JsonParsable {

    JsonValue parse() throws IOException;
}
