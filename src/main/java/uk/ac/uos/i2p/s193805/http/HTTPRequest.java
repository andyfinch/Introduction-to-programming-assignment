package uk.ac.uos.i2p.s193805.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 02/11/2018
 * Time: 14:06
 */

public class HTTPRequest {

   private int response;
   private String body;

   public void sendHTTPRequest(String urlAddress, String contentType, String HTTPMethod) throws Exception
   {
       URL url = new URL(urlAddress);
       HttpURLConnection con = (HttpURLConnection) url.openConnection();
       con.setRequestMethod(HTTPMethod.toUpperCase());
       response = con.getResponseCode();
       BufferedReader in = new BufferedReader(
               new InputStreamReader(con.getInputStream()));
       String inputLine;
       StringBuilder content = new StringBuilder();
       while ((inputLine = in.readLine()) != null)
       {
           content.append(inputLine);
       }
       in.close();
       con.disconnect();

       body = content.toString();
       

   }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
