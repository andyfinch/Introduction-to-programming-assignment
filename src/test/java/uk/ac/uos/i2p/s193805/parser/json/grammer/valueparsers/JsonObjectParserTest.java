package uk.ac.uos.i2p.s193805.parser.json.grammer.valueparsers;

import org.junit.jupiter.api.Test;
import uk.ac.uos.i2p.s193805.parser.LexParser;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;
import uk.ac.uos.i2p.s193805.parser.exceptions.JsonParseException;
import uk.ac.uos.i2p.s193805.parser.json.JSONParser;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonObject;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonValue;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class JsonObjectParserTest {

    @Test
    void testSimpleKeyString() throws IOException {


        JsonObject data = getParser("{\n" +
                "  \"instruction\": \"add\"\n" +
                "}\n").parse();

        assertEquals("add", data.getString("instruction"));

    }

    @Test
    void testTaskJson() throws IOException {

        JsonObject data = getParser("{\n" +
                "  \"instruction\": \"add\",\n" +
                "  \"parameters\": [\"23\",45],\n" +
                "  \"response URL\": \"/answer/d3ae45\"\n" +
                "}").parse();

        assertSame(JsonValue.ValueType.JSON_STRING, data.getJsonValue("instruction").getJsonValueType());
        assertEquals("add", data.getString("instruction"));
        assertSame(JsonValue.ValueType.JSON_ARRAY, data.getJsonValue("parameters").getJsonValueType());
        assertSame(JsonValue.ValueType.JSON_STRING, data.getJsonArray("parameters").getJsonValue(0).getJsonValueType());
        assertEquals("23", data.getJsonArray("parameters").getString(0));
        assertSame(JsonValue.ValueType.JSON_NUMBER, data.getJsonArray("parameters").getJsonValue(1).getJsonValueType());
        assertEquals(45, data.getJsonArray("parameters").getInt(1));
        assertEquals("/answer/d3ae45", data.getString("response URL"));

    }





    @Test
    void testSimpleKeyNumber() throws IOException {

        JsonObject data = new JSONParser(new StringReader("{\n" +
                "  \"number\": 12\n" +
                "}")).parse();

        assertEquals(12, data.getInt("number"));

    }


    @Test
    void testKeyValueNotNumber() throws IOException {

        JsonObject data = getParser("{\n" +
                "  \"number\": \"A\"\n" +
                "}").parse();

        try {
            data.getJSONNumber("number");
            fail("shouldn't get here");
        }
        catch (RuntimeException re)
        {
            assertEquals("Requested key is not a JSON_NUMBER. It is a JSON_STRING", re.getMessage());
        }


    }


    @Test
    void testSimpleKeyBoolean() throws IOException {

        JsonObject data = getParser("{\n" +
                "  \"instruction\": true\n" +
                "}\n").parse();

        assertSame(JsonValue.ValueType.JSON_BOOLEAN, data.getJsonValue("instruction").getJsonValueType());
        assertTrue(data.getBoolean("instruction"));

    }

    @Test
    void testSimpleKeyNull() throws IOException {

        JsonObject data = new JSONParser(new StringReader("{\n" +
                "  \"instruction\": null\n" +
                "}\n")).parse();

        assertNull(data.getJsonValue("instruction"));

    }

    @Test
    void testNested() throws IOException {

        JsonObject data = new JSONParser(new StringReader("{\n" +
                "  \"instruction\": \"add\",\n" +
                "  \"nested\" : {\n" +
                "    \"insideNested\": \"nestedString\"\n" +
                "  } \n" +
                "}")).parse();

        assertEquals("add", data.getString("instruction"));
        assertEquals("nestedString", data.getJsonObject("nested").getString("insideNested"));

    }

    @Test
    void testSimpleArray() throws IOException {

        JsonObject data = new JSONParser(new StringReader("{\n" +
                "  \"parameters\": [\n" +
                "    \"23\",\n" +
                "    45\n" +
                "  ]\n" +
                "}")).parse();

        assertEquals(2, data.getJsonArray("parameters").jsonValues.size());
        assertEquals("23", data.getJsonArray("parameters").getString(0));
        assertEquals(45, data.getJsonArray("parameters").getInt(1));

    }

    @Test
    void testObjectArray() throws IOException {

        JsonObject data = new JSONParser(new StringReader("{\n" +
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
                "}")).parse();

        assertEquals(2, data.getJsonArray("additionalDocument").jsonValues.size());
        assertEquals("1", data.getJsonArray("additionalDocument").getJsonObject(0).getString("catagoryCode"));
        assertEquals("2", data.getJsonArray("additionalDocument").getJsonObject(1).getString("catagoryCode"));

    }

    @Test
    void testMissingOpenBrace() throws IOException {


        assertParseValueException("\n" +
                "  \"instruction\": \"add\",\n" +
                "  \"parameters\": [\"23\",45],\n" +
                "  \"response URL\": \"/answer/d3ae45\"\n" +
                "}}", "JSON Object must start with {");


    }


    @Test
    void testMissingClosingBrace() throws IOException {


        assertParseException("\n {" +
                "  \"instruction\": \"add\"\n" +
                "\n", "JSON Object must end with }");

    }

    @Test
    void testNested2() throws IOException {
        JsonObject data = new JSONParser(new StringReader("{\"additionalDocument\": [\n" +
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
                "    \"name\": \"mcp\",\n" +
                "    \"address\": {\n" +
                "      \"line\": \"\",\n" +
                "      \"city\": \"\",\n" +
                "      \"countryCode\": \"\",\n" +
                "      \"postCode\": \"\"\n" +
                "    },\n" +
                "    \"id\": \"\"\n" +
                "  }}")).parse();

        assertEquals("1", data.getJsonArray("additionalDocument").getJsonObject(0).getString("catagoryCode"));
        assertEquals("mcp", data.getJsonObject("exporter").getString("name"));
    }


    @Test
    void testSuperNested() throws IOException {

        JsonObject data = new JSONParser(new StringReader("{\n" +
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
                "}")).parse();
        assertEquals("MCPMSM3122018104343006", data.getJsonObject("declaration").getString("functionalReferenceId"));
        assertEquals(2, data.getJsonObject("declaration").getJsonArray("additionalDocument").jsonValues.size());
        assertEquals("1", data.getJsonObject("declaration").getJsonArray("additionalDocument").getJsonObject(0).getString("catagoryCode"));
        assertEquals("type1", data.getJsonObject("declaration").getJsonObject("goodsShipment").getJsonArray("governmentAgencyGoodsItem").getJsonObject(0)
                .getJsonObject("commodity").getJsonArray("dutyTaxFee").getJsonObject(0).getString("typeCode"));


    }


    private void assertParseException(String json, String expectedMessage) throws IOException {
        try
        {
            new JSONParser(new StringReader(json)).parse();

            fail();
        } catch (JsonParseException e)
        {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    private void assertParseValueException(String json, String expectedMessage) throws IOException {
        try
        {
            getParser(json).parse();

            fail();
        } catch (JsonParseException | IllegalArgumentException e)
        {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    private JsonObjectParser getParser(String json) throws IOException
    {

        LexParser lexParser = new LexParser(new StringReader(json));
        PushbackLexParser pushBackLexParser = new PushbackLexParser(lexParser);
        return new JsonObjectParser(pushBackLexParser);


    }
}