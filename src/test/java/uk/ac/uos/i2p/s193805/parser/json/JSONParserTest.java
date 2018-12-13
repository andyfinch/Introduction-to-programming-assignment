package uk.ac.uos.i2p.s193805.parser.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.uos.i2p.s193805.parser.exceptions.JsonParseException;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonObject;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonString;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonValue;

import javax.json.stream.JsonParser;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

public class JSONParserTest {



    @Test
    void testSimpleKeyString() throws IOException {

        JSONParser jsonParser = new JSONParser(new StringReader("{\n" +
                "  \"instruction\": \"add\"\n" +
                "}\n"));
        jsonParser.parse();
        JsonObject data = jsonParser.getJsonObject();
        assertEquals("add", data.getString("instruction"));

    }

    @Test
    void testSimpleKeyString2() throws IOException {


        JSONParser jsonParser = new JSONParser(new StringReader("\"this is a string\""));
        jsonParser.parse();
        JsonValue data = jsonParser.jsonValue;
        assertTrue(data instanceof JsonString);

    }

    @Test
    void testSimpleKeyString_X2() throws IOException {

        JSONParser jsonParser = new JSONParser(new StringReader("{\n" +
                "  \"instruction\" : \"add\",\n" +
                "  \"response URL\": \"/answer/d3ae45\"\n" +
                "}"));
        jsonParser.parse();
        JsonObject data = jsonParser.getJsonObject();

        assertEquals("/answer/d3ae45", data.getString("response URL"));

    }

    @Test
    void testSimpleKeyNumber() throws IOException {

        JSONParser jsonParser = new JSONParser(new StringReader("{\n" +
                "  \"number\": 12\n" +
                "}"));
        jsonParser.parse();
        JsonObject data = jsonParser.getJsonObject();

        assertEquals(12, data.getInt("number"));

    }

    @Test
    void testSimpleKeyNegativeNumber() throws IOException {

        JSONParser jsonParser = new JSONParser(new StringReader("{\n" +
                "  \"number\": -12\n" +
                "}"));
        jsonParser.parse();
        JsonObject data = jsonParser.getJsonObject();

        assertEquals(-12, data.getInt("number"));

    }

    @Test
    void testSimpleKeyDecimalNumber() throws IOException {

        JSONParser jsonParser = new JSONParser(new StringReader("{\n" +
                "  \"number\": 1.1\n" +
                "}"));
        jsonParser.parse();
        JsonObject data = jsonParser.getJsonObject();

        assertEquals(1.1, data.getJSONNumber("number").number.doubleValue());

    }

    @Test
    void testSimpleKeyMinusDecimalNumber() throws IOException {

        JSONParser jsonParser = new JSONParser(new StringReader("{\n" +
                "  \"number\": -1.1\n" +
                "}"));
        jsonParser.parse();
        JsonObject data = jsonParser.getJsonObject();

        assertEquals(-1.1, data.getJSONNumber("number").number.doubleValue());

    }

    @Test
    void testSimpleKeyDecimalNumberLong() throws IOException {

        JSONParser jsonParser = new JSONParser(new StringReader("{\n" +
                "  \"number\": 12345.12345\n" +
                "}"));
        jsonParser.parse();
        JsonObject data = jsonParser.getJsonObject();

        assertEquals(12345.12345, data.getJSONNumber("number").number.doubleValue());

    }

    @Test
    void testSimpleKeyExponentNumber() throws IOException {

        JSONParser jsonParser = new JSONParser(new StringReader("{\n" +
                "  \"number\": 1e+2\n" +
                "}"));
        jsonParser.parse();
        JsonObject data = jsonParser.getJsonObject();

        assertEquals(1e+2, data.getJSONNumber("number").number.doubleValue());

    }

    @Test
    void testSimpleKeyDecimalExponentNumber() throws IOException {

        JSONParser jsonParser = new JSONParser(new StringReader("{\n" +
                "  \"number\": 1.1e+2\n" +
                "}"));
        jsonParser.parse();
        JsonObject data = jsonParser.getJsonObject();

        assertEquals(1.1e+2, data.getJSONNumber("number").number.doubleValue());

    }

    @Test
    void testKeyValueNotNumber() throws IOException {

        JSONParser jsonParser = new JSONParser(new StringReader("{\n" +
                "  \"number\": \"A\"\n" +
                "}"));
        jsonParser.parse();
        JsonObject data = jsonParser.getJsonObject();

        try {
            data.getJSONNumber("number");
            fail("shouldn't get here");
        }
        catch (RuntimeException re)
        {
            assertEquals("Requested key is not a JsonString. It is a JsonNumber", re.getMessage());
        }


    }

    @Test
    void testKeyStringNumberCombo() throws IOException {

        JSONParser jsonParser = new JSONParser(new StringReader("{\n" +
                "  \"instruction\" : \"add\",\n" +
                "  \"number\" : 12,\n" +
                "  \"response URL\": \"/answer/d3ae45\",\n" +
                "  \"server\": \"Test1Server2\"\n" +
                "}\n"));
        jsonParser.parse();
        JsonObject data = jsonParser.getJsonObject();

        assertEquals("add", data.getString("instruction"));
        assertEquals("/answer/d3ae45", data.getString("response URL"));
        assertEquals(12, data.getInt("number"));
        assertEquals("Test1Server2", data.getString("server"));

    }

    @Test
    void testSimpleKeyBoolean() throws IOException {

        JSONParser jsonParser = new JSONParser(new StringReader("{\n" +
                "  \"instruction\": true\n" +
                "}\n"));
        jsonParser.parse();
        JsonObject data = jsonParser.getJsonObject();

        assertTrue(data.getBoolean("instruction"));

    }

    @Test
    void testSimpleKeyNull() throws IOException {

        JSONParser jsonParser = new JSONParser(new StringReader("{\n" +
                "  \"instruction\": null\n" +
                "}\n"));
        jsonParser.parse();
        JsonObject data = jsonParser.getJsonObject();

        assertNull(data.getJsonValue("instruction"));

    }

    @Test
    void testNested() throws IOException {

        JSONParser jsonParser = new JSONParser(new StringReader("{\n" +
                "  \"instruction\": \"add\",\n" +
                "  \"nested\" : {\n" +
                "    \"insideNested\": \"nestedString\"\n" +
                "  } \n" +
                "}"));
        jsonParser.parse();
        JsonObject data = jsonParser.getJsonObject();

        assertEquals("add", data.getString("instruction"));
        assertEquals("nestedString", data.getJsonObject("nested").getString("insideNested"));

    }

    @Test
    void testSimpleArray() throws IOException {

        JSONParser jsonParser = new JSONParser(new StringReader("{\n" +
                "  \"parameters\": [\n" +
                "    \"23\",\n" +
                "    45\n" +
                "  ]\n" +
                "}"));
        jsonParser.parse();
        JsonObject data = jsonParser.getJsonObject();

        assertEquals(2, data.getJsonArray("parameters").jsonValues.size());
        assertEquals("23", data.getJsonArray("parameters").getString(0));
        assertEquals(45, data.getJsonArray("parameters").getInt(1));

    }

    @Test
    void testObjectArray() throws IOException {

        JSONParser jsonParser = new JSONParser(new StringReader("{\n" +
                "  \"additionalDocument\": [\n" +
                "    {\n" +
                "      \"catagoryCode\": \"1\",\n" +
                "      \"id\": \"\",\n" +
                "      \"typeCode\": \"DAN\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"catagoryCode\": \"2\",\n" +
                "      \"id\": \"\",\n" +
                "      \"typeCode\": \"DAN\"\n" +
                "    }\n" +
                "  ]\n" +
                "}"));
        jsonParser.parse();
        JsonObject data = jsonParser.getJsonObject();

        assertEquals(2, data.getJsonArray("additionalDocument").jsonValues.size());
        assertEquals("1", data.getJsonArray("additionalDocument").getJsonObject(0).getString("catagoryCode"));
        assertEquals("2", data.getJsonArray("additionalDocument").getJsonObject(1).getString("catagoryCode"));

    }

    @Test
    void testMissingOpeningBrace() throws IOException {

        JSONParser jsonParser = new JSONParser(new StringReader("\n" +
                "  \"instruction\" : \"add\",\n" +
                "  \"response URL\": \"/answer/d3ae45\"\n" +
                "}"));
        jsonParser.parse();
        JsonObject data = jsonParser.getJsonObject();

        assertParseException("\n" +
                "  \"instruction\": \"add\"\n" +
                "}\n", "JSON Object must start with {");

    }

    @Test
    void testMissingClosingBrace() throws IOException {

        JSONParser jsonParser = new JSONParser(new StringReader("{\n" +
                "  \"instruction\" : \"add\",\n" +
                "  \"response URL\": \"/answer/d3ae45\"\n" +
                "}"));
        jsonParser.parse();
        JsonObject data = jsonParser.getJsonObject();

        assertParseException("\n {" +
                "  \"instruction\": \"add\"\n" +
                "\n", "JSON Object must end with }");

    }

    /*@Test
    void testSuperNestedX() throws IOException {
          JSONParser jsonParser = new JSONParser();
          JsonObject data = jsonParser.parse(new StringReader("{\"additionalDocument\": [\n" +
                  "    {\n" +
                  "      \"catagoryCode\": \"1\",\n" +
                  "      \"id\": \"\",\n" +
                  "      \"typeCode\": \"DAN\"\n" +
                  "    },\n" +
                  "    {\n" +
                  "      \"catagoryCode\": \"2\",\n" +
                  "      \"id\": \"\",\n" +
                  "      \"typeCode\": \"DAN\"\n" +
                  "    }\n" +
                  "  ],\n" +
                  "  \"exporter\": {\n" +
                  "    \"name\": \"\",\n" +
                  "    \"address\": {\n" +
                  "      \"line\": \"\",\n" +
                  "      \"city\": \"\",\n" +
                  "      \"countryCode\": \"\",\n" +
                  "      \"postCode\": \"\"\n" +
                  "    },\n" +
                  "    \"id\": \"\"\n" +
                  "  }}"));

        System.out.println(data);
    }

    @Test
    void testSuperNestedXX() throws IOException {
        JSONParser jsonParser = new JSONParser();
        JsonObject data = jsonParser.parse(new StringReader("{ \"exporter\": {\n" +
                "    \"address\": {\n" +
                "      \"line\": \"\"\n" +
                "    },\n" +
                "    \"id\": \"\"\n" +
                "  }}"));

        System.out.println(data);
    }*/


    /*@Test
    void testSuperNested() throws IOException {

        JSONParser jsonParser = new JSONParser(new StringReader("{\n" +
                "  \"instruction\" : \"add\",\n" +
                "  \"response URL\": \"/answer/d3ae45\"\n" +
                "}"));
        jsonParser.parse();
        JsonObject data = jsonParser.getJsonObject();

        JSONParser jsonParser = new JSONParser();
        JsonObject data = jsonParser.parse(new StringReader("{\n" +
                "  \"declaration\": {\n" +
                "    \"acceptanceDateTime\": \"\",\n" +
                "    \"typeCode\": \"\",\n" +
                "    \"additionalTypeCode\": \"\",\n" +
                "    \"goodsItemQuantity\": \"\",\n" +
                "    \"functionalReferenceId\": \"MCPMSM3122018104343006\",\n" +
                "    \"additionalDocument\": [\n" +
                "      {\n" +
                "        \"catagoryCode\": \"1\",\n" +
                "        \"id\": \"\",\n" +
                "        \"typeCode\": \"DAN\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"catagoryCode\": \"2\",\n" +
                "        \"id\": \"\",\n" +
                "        \"typeCode\": \"DAN\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"exporter\": {\n" +
                "      \"name\": \"\",\n" +
                "      \"address\": {\n" +
                "        \"line\": \"\",\n" +
                "        \"city\": \"\",\n" +
                "        \"countryCode\": \"\",\n" +
                "        \"postCode\": \"\"\n" +
                "      },\n" +
                "      \"id\": \"\"\n" +
                "    },\n" +
                "    \"declarant\": {\n" +
                "      \"name\": \"\",\n" +
                "      \"address\": {\n" +
                "        \"line\": \"\",\n" +
                "        \"city\": \"\",\n" +
                "        \"countryCode\": \"\",\n" +
                "        \"postCode\": \"\"\n" +
                "      },\n" +
                "      \"id\": \"\"\n" +
                "    },\n" +
                "    \"agent\": {\n" +
                "      \"name\": \"\",\n" +
                "      \"address\": {\n" +
                "        \"line\": \"\",\n" +
                "        \"city\": \"\",\n" +
                "        \"countryCode\": \"\",\n" +
                "        \"postCode\": \"\"\n" +
                "      },\n" +
                "      \"id\": \"\"\n" +
                "    },\n" +
                "    \"authorisationHolder\": [\n" +
                "      {\n" +
                "        \"id\": \"\",\n" +
                "        \"categoryCode\": \"\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"invoiceAmount\": \"\",\n" +
                "    \"invoiceAmountCurrencyID\": \"\",\n" +
                "    \"currencyExchange\": {\n" +
                "      \"currencyTypeCode\": \"\",\n" +
                "      \"rateNumeric\": \"\"\n" +
                "    },\n" +
                "    \"exitOffice\": {\n" +
                "      \"id\": \"\"\n" +
                "    },\n" +
                "    \"presentationOffice\": {\n" +
                "      \"id\": \"\"\n" +
                "    },\n" +
                "    \"supervisingOffice\": {\n" +
                "      \"id\": \"\"\n" +
                "    },\n" +
                "    \"totalPackageQuantity\": \"\",\n" +
                "    \"borderTransportMeans\": {\n" +
                "      \"modeCode\": \"\",\n" +
                "      \"registrationNationalityCode\": \"\"\n" +
                "    },\n" +
                "    \"obligationGuarantee\": [\n" +
                "      {\n" +
                "        \"referenceId\": \"\",\n" +
                "        \"id\": \"\",\n" +
                "        \"accessCode\": \"\",\n" +
                "        \"amountAmount\": \"\",\n" +
                "        \"amountAmountCurrencyId\": \"\",\n" +
                "        \"guaranteeOffice\": {\n" +
                "          \"id\": \"\"\n" +
                "        },\n" +
                "        \"securityDetailsCode\": \"\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"goodsShipment\": {\n" +
                "      \"previousDocument\": [\n" +
                "        {\n" +
                "          \"categoryCode\": \"\",\n" +
                "          \"typeCode\": \"\",\n" +
                "          \"id\": \"\",\n" +
                "          \"lineNumeric\": \"\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"ucr\": {\n" +
                "        \"traderAssignedReferenceId\": \"\"\n" +
                "      },\n" +
                "      \"warehouse\": {\n" +
                "        \"typeCode\": \"\",\n" +
                "        \"id\": \"\"\n" +
                "      },\n" +
                "      \"consignee\": {\n" +
                "        \"name\": \"\",\n" +
                "        \"address\": {\n" +
                "          \"line\": \"\",\n" +
                "          \"city\": \"\",\n" +
                "          \"countryCode\": \"\",\n" +
                "          \"postCode\": \"\"\n" +
                "        },\n" +
                "        \"id\": \"\"\n" +
                "      },\n" +
                "      \"importer\": {\n" +
                "        \"name\": \"\",\n" +
                "        \"address\": {\n" +
                "          \"line\": \"\",\n" +
                "          \"city\": \"\",\n" +
                "          \"countryCode\": \"\",\n" +
                "          \"postCode\": \"\"\n" +
                "        },\n" +
                "        \"id\": \"\"\n" +
                "      },\n" +
                "      \"seller\": {\n" +
                "        \"name\": \"\",\n" +
                "        \"address\": {\n" +
                "          \"line\": \"\",\n" +
                "          \"city\": \"\",\n" +
                "          \"countryCode\": \"\",\n" +
                "          \"postCode\": \"\"\n" +
                "        },\n" +
                "        \"id\": \"\",\n" +
                "        \"contact\": {\n" +
                "          \"communication\": {\n" +
                "            \"id\": \"\"\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      \"buyer\": {\n" +
                "        \"name\": \"\",\n" +
                "        \"address\": {\n" +
                "          \"line\": \"\",\n" +
                "          \"city\": \"\",\n" +
                "          \"countryCode\": \"\",\n" +
                "          \"postCode\": \"\"\n" +
                "        },\n" +
                "        \"id\": \"\",\n" +
                "        \"contact\": {\n" +
                "          \"communication\": {\n" +
                "            \"id\": \"\"\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      \"aeoMutualRecognitionParty\": [\n" +
                "        {\n" +
                "          \"id\": \"\",\n" +
                "          \"roleCode\": \"\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"domesticTaxParty\": [\n" +
                "        {\n" +
                "          \"id\": \"\",\n" +
                "          \"roleCode\": \"\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"tradeTerms\": {\n" +
                "        \"conditionCode\": \"\",\n" +
                "        \"locationId\": \"\",\n" +
                "        \"locationName\": \"\"\n" +
                "      },\n" +
                "      \"destination\": {\n" +
                "        \"countryCode\": \"\"\n" +
                "      },\n" +
                "      \"exportCountry\": {\n" +
                "        \"id\": {\n" +
                "          \"code\": \"FR\",\n" +
                "          \"name\": \"FRANCE\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"consignment\": {\n" +
                "        \"goodsLocation\": {\n" +
                "          \"name\": \"\",\n" +
                "          \"address\": {\n" +
                "            \"line\": \"\",\n" +
                "            \"city\": \"\",\n" +
                "            \"countryCode\": \"GB\",\n" +
                "            \"postCode\": \"\",\n" +
                "            \"typeCode\": \"U\"\n" +
                "          },\n" +
                "          \"id\": \"\",\n" +
                "          \"typeCode\": \"A\"\n" +
                "        },\n" +
                "        \"containerCode\": \"\",\n" +
                "        \"arrivalTransportMeans\": {\n" +
                "          \"modeCode\": \"\",\n" +
                "          \"identificationTypeCode\": \"\",\n" +
                "          \"id\": \"\"\n" +
                "        },\n" +
                "        \"transportEquipment\": {\n" +
                "          \"id\": [\n" +
                "            \"\",\n" +
                "            \"\",\n" +
                "            \"\",\n" +
                "            \"\",\n" +
                "            \"\",\n" +
                "            \"\"\n" +
                "          ]\n" +
                "        },\n" +
                "        \"loadingLocation\": {\n" +
                "          \"id\": \"\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"transactionNatureCode\": \"\",\n" +
                "      \"customsValuation\": {\n" +
                "        \"chargeDeduction\": [\n" +
                "          {\n" +
                "            \"otherChargeDeductionAmount\": \"\",\n" +
                "            \"currencyId\": \"\",\n" +
                "            \"chargesTypeCode\": \"\"\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"governmentAgencyGoodsItem\": [\n" +
                "        {\n" +
                "          \"sequenceNumeric\": \"\",\n" +
                "          \"governmentProcedure\": [\n" +
                "            {\n" +
                "              \"currentCode\": \"\",\n" +
                "              \"previousCode\": \"\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"currentCode\": \"\",\n" +
                "              \"previousCode\": \"\"\n" +
                "            }\n" +
                "          ],\n" +
                "          \"additionalInformation\": [\n" +
                "            {\n" +
                "              \"statementCode\": \"\",\n" +
                "              \"statementDescription\": \"\"\n" +
                "            }\n" +
                "          ],\n" +
                "          \"additionalDocument\": [\n" +
                "            {\n" +
                "              \"catagoryCode\": \"\",\n" +
                "              \"typeCode\": \"\",\n" +
                "              \"id\": \"\",\n" +
                "              \"lpcoExemptionCode\": \"\",\n" +
                "              \"name\": \"\",\n" +
                "              \"submitter\": {\n" +
                "                \"name\": \"\"\n" +
                "              },\n" +
                "              \"effectiveDateTime\": \"\",\n" +
                "              \"writeOff\": {\n" +
                "                \"quantityQuantity\": \"\",\n" +
                "                \"unitCode\": \"\"\n" +
                "              }\n" +
                "            }\n" +
                "          ],\n" +
                "          \"commodity\": {\n" +
                "            \"dutyTaxFee\": [\n" +
                "              {\n" +
                "                \"typeCode\": \"type1\",\n" +
                "                \"specificTaxBaseQuantity\": \"\",\n" +
                "                \"specificTaxBaseQuantityUnitCode\": \"\",\n" +
                "                \"taxRateNumeric\": \"\",\n" +
                "                \"payment\": {\n" +
                "                  \"paymentAmount\": \"\",\n" +
                "                  \"paymentAmountCurrencyId\": \"\",\n" +
                "                  \"methodCode\": \"\",\n" +
                "                  \"taxAssessedAmount\": \"\",\n" +
                "                  \"taxAssessedAmountCurrencyId\": \"\"\n" +
                "                },\n" +
                "                \"dutyRegimeCode\": \"\",\n" +
                "                \"quotaOrderId\": \"\"\n" +
                "              }\n" +
                "            ],\n" +
                "            \"invoiceLine\": {\n" +
                "              \"itemChargeAmount\": \"\",\n" +
                "              \"itemChargeAmountCurrencyID\": \"\"\n" +
                "            },\n" +
                "            \"customsValuation\": {\n" +
                "              \"chargeDeduction\": [\n" +
                "                {\n" +
                "                  \"otherChargeDeductionAmount\": \"\",\n" +
                "                  \"currencyId\": \"\",\n" +
                "                  \"chargesTypeCode\": \"\"\n" +
                "                }\n" +
                "              ],\n" +
                "              \"methodCode\": \"\"\n" +
                "            },\n" +
                "            \"goodsMeasure\": {\n" +
                "              \"netNetWeightMeasure\": \"\",\n" +
                "              \"netNetWeightMeasureUnitCode\": \"\",\n" +
                "              \"tariffQuantity\": \"\",\n" +
                "              \"grossMassMeasure\": \"\",\n" +
                "              \"grossMassMeasureUnitCode\": \"\"\n" +
                "            },\n" +
                "            \"classification\": [\n" +
                "              {\n" +
                "                \"id\": \"\",\n" +
                "                \"identificationTypeCode\": \"CV\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"id\": \"\",\n" +
                "                \"identificationTypeCode\": \"TSP\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"id\": \"\",\n" +
                "                \"identificationTypeCode\": \"TRC\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"id\": \"\",\n" +
                "                \"identificationTypeCode\": \"TRA\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"id\": \"\",\n" +
                "                \"identificationTypeCode\": \"GN\"\n" +
                "              }\n" +
                "            ],\n" +
                "            \"description\": \"\",\n" +
                "            \"transportEquipment\": {\n" +
                "              \"id\": [\n" +
                "                \"\",\n" +
                "                \"\",\n" +
                "                \"\",\n" +
                "                \"\",\n" +
                "                \"\",\n" +
                "                \"\"\n" +
                "              ]\n" +
                "            }\n" +
                "          },\n" +
                "          \"valuationAdjustment\": {\n" +
                "            \"additionalCode\": \"\"\n" +
                "          },\n" +
                "          \"origin\": [\n" +
                "            {\n" +
                "              \"countryCode\": \"\",\n" +
                "              \"typeCode\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"countryCode\": \"\",\n" +
                "              \"typeCode\": \"2\"\n" +
                "            }\n" +
                "          ],\n" +
                "          \"packaging\": [\n" +
                "            {\n" +
                "              \"typeCode\": \"\",\n" +
                "              \"quantityQuantity\": \"\",\n" +
                "              \"marksNumberId\": \"\"\n" +
                "            }\n" +
                "          ],\n" +
                "          \"statisticalValueAmount\": \"\",\n" +
                "          \"statisticalValueAmountCurrencyId\": \"\",\n" +
                "          \"aeoMutualRecognitionParty\": [\n" +
                "            {\n" +
                "              \"id\": \"\",\n" +
                "              \"roleCode\": \"\"\n" +
                "            }\n" +
                "          ],\n" +
                "          \"buyer\": {\n" +
                "            \"name\": \"\",\n" +
                "            \"address\": {\n" +
                "              \"line\": \"\",\n" +
                "              \"city\": \"\",\n" +
                "              \"countryCode\": \"\",\n" +
                "              \"postCode\": \"\"\n" +
                "            },\n" +
                "            \"id\": \"\",\n" +
                "            \"contact\": {\n" +
                "              \"communication\": {\n" +
                "                \"id\": \"\"\n" +
                "              }\n" +
                "            }\n" +
                "          },\n" +
                "          \"consignee\": {\n" +
                "            \"name\": \"\",\n" +
                "            \"address\": {\n" +
                "              \"line\": \"\",\n" +
                "              \"city\": \"\",\n" +
                "              \"countryCode\": \"\",\n" +
                "              \"postCode\": \"\"\n" +
                "            },\n" +
                "            \"id\": \"\"\n" +
                "          },\n" +
                "          \"consignor\": {\n" +
                "            \"name\": \"\",\n" +
                "            \"address\": {\n" +
                "              \"line\": \"\",\n" +
                "              \"city\": \"\",\n" +
                "              \"countryCode\": \"\",\n" +
                "              \"postCode\": \"\"\n" +
                "            },\n" +
                "            \"id\": \"\"\n" +
                "          },\n" +
                "          \"destination\": {\n" +
                "            \"countryCode\": \"\"\n" +
                "          },\n" +
                "          \"domesticTaxParty\": [\n" +
                "            {\n" +
                "              \"id\": \"\",\n" +
                "              \"roleCode\": \"\"\n" +
                "            }\n" +
                "          ],\n" +
                "          \"exportCountry\": {\n" +
                "            \"id\": \"\"\n" +
                "          },\n" +
                "          \"previousDocument\": [\n" +
                "            {\n" +
                "              \"categoryCode\": \"\",\n" +
                "              \"typeCode\": \"\",\n" +
                "              \"id\": \"\",\n" +
                "              \"lineNumeric\": \"\"\n" +
                "            }\n" +
                "          ],\n" +
                "          \"seller\": {\n" +
                "            \"name\": \"\",\n" +
                "            \"address\": {\n" +
                "              \"line\": \"\",\n" +
                "              \"city\": \"\",\n" +
                "              \"countryCode\": \"\",\n" +
                "              \"postCode\": \"\"\n" +
                "            },\n" +
                "            \"id\": \"\",\n" +
                "            \"contact\": {\n" +
                "              \"communication\": {\n" +
                "                \"id\": \"\"\n" +
                "              }\n" +
                "            }\n" +
                "          },\n" +
                "          \"transactionNatureCode\": \"\",\n" +
                "          \"ucr\": {\n" +
                "            \"traderAssignedReferenceId\": \"\"\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    \"totalGrossMassMeasure\": \"\"\n" +
                "  }\n" +
                "}"));
        assertEquals("MCPMSM3122018104343006", data.getJsonObject("declaration").getString("functionalReferenceId"));
        assertEquals(2, data.getJsonObject("declaration").getJsonArray("additionalDocument").jsonValues.size());
        assertEquals("1", data.getJsonObject("declaration").getJsonArray("additionalDocument").getJsonObject(0).getString("catagoryCode"));
        assertEquals("type1", data.getJsonObject("declaration").getJsonObject("goodsShipment").getJsonArray("governmentAgencyGoodsItem").getJsonObject(0)
                .getJsonObject("commodity").getJsonArray("dutyTaxFee").getJsonObject(0).getString("typeCode"));
        

    }*/


    private void assertParseException(String json, String expectedMessage) throws IOException {
        try
        {
            JSONParser jsonParser = new JSONParser(new StringReader(json));
            jsonParser.parse();

            fail();
        } catch (JsonParseException e)
        {
            assertEquals(expectedMessage, e.getMessage());
        }
    }
}
