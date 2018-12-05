package uk.ac.uos.i2p.s193805.http;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 02/11/2018
 * Time: 14:06
 */

public class HTTPRequest {

    private int response;
    private String body;

    public void sendHTTPRequest(String urlAddress, String contentType, String HTTPMethod) throws Exception {
        URL url = new URL(urlAddress);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("webdefence.global.blackspider.com", 8081));
        HttpURLConnection con = (HttpURLConnection) url.openConnection(proxy);
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

    public void sendHTTPGetRequest(String urlAddress) throws Exception {
        URL url = new URL(urlAddress);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("webdefence.global.blackspider.com", 8081));
        HttpURLConnection con = (HttpURLConnection) url.openConnection(proxy);
        con.setRequestMethod("GET");
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

        this.body = content.toString();
    }

    public void sendHTTPPostRequest(String urlAddress, String contentType, String body) throws Exception {
        URL url = new URL(urlAddress);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("webdefence.global.blackspider.com", 8081));
        HttpURLConnection con = (HttpURLConnection) url.openConnection(proxy);
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
        osw.write(body);
        osw.flush();
        osw.close();
        os.close();
        con.connect();

        response = con.getResponseCode();
        
        if ( response == 200 )
        {
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
            this.body = content.toString();
        }



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
