package uk.ac.uos.i2p.s193805.parser.json.grammer;

import java.io.IOException;

/**
 * Interface allowing any Json Parsing class to be a JsonParsable type
 */
public interface JsonParsable {

    /**
     * Parse json value.
     *
     * @return the json value
     * @throws IOException the io exception
     */
    JsonValue parse() throws IOException;
}
