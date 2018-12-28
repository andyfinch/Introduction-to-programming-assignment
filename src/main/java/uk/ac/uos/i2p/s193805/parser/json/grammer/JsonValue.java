package uk.ac.uos.i2p.s193805.parser.json.grammer;

public interface JsonValue {

    enum ValueType
    {
        JSON_OBJECT,
        JSON_ARRAY,
        JSON_STRING,
        JSON_NUMBER,
        JSON_BOOLEAN
    }

    ValueType getJsonValueType();

}
