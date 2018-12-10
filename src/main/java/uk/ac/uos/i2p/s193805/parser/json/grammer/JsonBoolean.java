package uk.ac.uos.i2p.s193805.parser.json.grammer;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 10/12/2018
 * Time: 16:17
 */

public class JsonBoolean implements JsonValue {

    public final Boolean booleanValue;

    public JsonBoolean(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }
}
