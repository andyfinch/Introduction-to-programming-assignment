package uk.ac.uos.i2p.s193805.http;

import org.junit.jupiter.api.Test;
import uk.ac.uos.i2p.s193805.taskhandling.task.Tasks;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 02/11/2018
 * Time: 14:13
 */

class HTTPRequestTest {

    @Test
    void testHTTP() {
        HTTPRequest httpRequest = new HTTPRequest();
        try
        {
            httpRequest.sendHTTPRequest("http://localhost:4500", null, "GET");
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("test");
    }

    @Test
    void testHTTPIntegration() {
        HTTPRequest httpRequest = new HTTPRequest();
        try
        {
            httpRequest.sendHTTPRequest("http://localhost:4500", null, "GET");
        } catch (Exception e)
        {
            e.printStackTrace();
        }


        System.out.println("test");
    }
}