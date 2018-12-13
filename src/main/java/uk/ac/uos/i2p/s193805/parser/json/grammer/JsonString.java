package uk.ac.uos.i2p.s193805.parser.json.grammer;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 10/12/2018
 * Time: 16:17
 */

public class JsonString implements JsonValue {

    public final String stringValue;

    public JsonString(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return stringValue;
    }

    @Override
    public ValueType getJsonValueType() {
        return ValueType.JSON_STRING;
    }
}
