package uk.ac.uos.i2p.s193805.parser.json;

import org.junit.jupiter.api.Test;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonObject;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

public class JSONParserTest {

    @Test
    void testSimpleKeyString() throws IOException {

        JSONParser jsonParser = new JSONParser();
        JsonObject data = jsonParser.parse(new StringReader("{\n" +
                "  \"instruction\": \"add\"\n" +
                "}\n"));
        assertEquals("add", data.getJSONString("instruction"));

    }

    @Test
    void testSimpleKeyString_X2() throws IOException {

        JSONParser jsonParser = new JSONParser();
        JsonObject data = jsonParser.parse(new StringReader("{\n" +
                "  \"instruction\" : \"add\",\n" +
                "  \"response URL\": \"/answer/d3ae45\"\n" +
                "}"));
        assertEquals("/answer/d3ae45", data.getJSONString("response URL"));

    }

    @Test
    void testSimpleKeyNumber() throws IOException {

        JSONParser jsonParser = new JSONParser();
        JsonObject data = jsonParser.parse(new StringReader("{\n" +
                "  \"number\": 12\n" +
                "}"));
        assertEquals(12, data.getJSONNumber("number").intValue());

    }

    @Test
    void testSimpleKeyNegativeNumber() throws IOException {

        JSONParser jsonParser = new JSONParser();
        JsonObject data = jsonParser.parse(new StringReader("{\n" +
                "  \"number\": -12\n" +
                "}"));
        assertEquals(-12, data.getJSONNumber("number").intValue());

    }

    @Test
    void testKeyValueNotNumber() throws IOException {

        JSONParser jsonParser = new JSONParser();
        JsonObject data = jsonParser.parse(new StringReader("{\n" +
                "  \"number\": \"A\"\n" +
                "}"));

        try {
            data.getJSONNumber("number");
            fail("shouldn't get here");
        }
        catch (RuntimeException re)
        {
            assertEquals("Cannot convert number to Integer A", re.getMessage());
        }


    }

    @Test
    void testKeyStringNumberCombo() throws IOException {

        JSONParser jsonParser = new JSONParser();
        JsonObject data = jsonParser.parse(new StringReader("{\n" +
                "  \"instruction\" : \"add\",\n" +
                "  \"number\" : 12,\n" +
                "  \"response URL\": \"/answer/d3ae45\",\n" +
                "  \"server\": \"Test1Server2\"\n" +
                "}\n"));
        assertEquals("add", data.getJSONString("instruction"));
        assertEquals("/answer/d3ae45", data.getJSONString("response URL"));
        assertEquals(12, data.getJSONNumber("number").intValue());
        assertEquals("Test1Server2", data.getJSONString("server"));

    }

    @Test
    void testSimpleKeyBoolean() throws IOException {

        JSONParser jsonParser = new JSONParser();
        JsonObject data = jsonParser.parse(new StringReader("{\n" +
                "  \"instruction\": true\n" +
                "}\n"));
        assertEquals(true, data.getJsonBoolean("instruction"));

    }

    @Test
    void testSimpleKeyNull() throws IOException {

        JSONParser jsonParser = new JSONParser();
        JsonObject data = jsonParser.parse(new StringReader("{\n" +
                "  \"instruction\": null\n" +
                "}\n"));
        assertNull(data.getJsonValue("instruction"));

    }

    @Test
    void testNested() throws IOException {

        JSONParser jsonParser = new JSONParser();
        JsonObject data = jsonParser.parse(new StringReader("{\n" +
                "  \"instruction\": \"add\",\n" +
                "  \"nested\" : {\n" +
                "    \"insideNested\": \"nestedString\"\n" +
                "  } \n" +
                "}"));
        assertEquals("add", data.getJSONString("instruction"));
        assertEquals("nestedString", data.getJsonObject("nested").getJSONString("insideNested"));

    }

    @Test
    void testSimpleArray() throws IOException {

        JSONParser jsonParser = new JSONParser();
        JsonObject data = jsonParser.parse(new StringReader("{\n" +
                "  \"parameters\": [\n" +
                "    \"23\",\n" +
                "    45\n" +
                "  ]\n" +
                "}"));
        assertEquals(2, data.getJsonArray("parameters").jsonValues.size());
        assertEquals("23", data.getJsonArray("parameters").jsonValues.get(0).object);
        assertEquals(45, data.getJsonArray("parameters").jsonValues.get(1).object);

    }

    @Test
    void testObjectArray() throws IOException {

        JSONParser jsonParser = new JSONParser();
        JsonObject data = jsonParser.parse(new StringReader("{\n" +
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
        /*assertEquals(2, data.getJsonArray("parameters").jsonValues.size());
        assertEquals("23", data.getJsonArray("parameters").jsonValues.get(0).object);
        assertEquals(45, data.getJsonArray("parameters").jsonValues.get(1).object);*/

    }


    @Test
    void testSuperNested() throws IOException {

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
        assertEquals("MCPMSM3122018104343006", data.getJsonObject("declaration").getJSONString("functionalReferenceId"));
        assertEquals(2, data.getJsonObject("declaration").getJsonArray("additionalDocument").jsonValues.size());
        assertEquals("1", data.getJsonObject("declaration").getJsonArrayList("additionalDocument").get(0).getJsonObject().getJSONString("catagoryCode"));
        assertEquals("type1", data.getJsonObject("declaration").getJsonObject("goodsShipment").getJsonArrayList("governmentAgencyGoodsItem")
                .get(0).getJsonObject().getJsonObject("commodity").getJsonArrayList("dutyTaxFee").get(0).getJsonObject().getJSONString("typeCode"));
        

    }
}
