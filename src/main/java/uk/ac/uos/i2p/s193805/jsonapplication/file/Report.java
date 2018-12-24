package uk.ac.uos.i2p.s193805.jsonapplication.file;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 24/12/2018
 * Time: 09:00
 */

public class Report implements FileWritable {

    private static final String BASE_PATH = "src/Results/";

    private String fileName;
    private String requestContent;
    private String requestURL;
    private String responseURL;
    private String responseSent;
    private int response;



    public String getFileName() {
        return BASE_PATH+fileName;
    }

    @Override
    public String getContent() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Content received from ").append(requestURL).append(":").append("\n\n");
        stringBuilder.append(requestContent).append("\n\n");


        if ( responseSent != null)
        {
            stringBuilder.append("Response sent to ");

            if (responseURL != null)
            {
                stringBuilder.append(responseURL);
            }
            else
            {
                stringBuilder.append(requestURL);
            }
            stringBuilder.append(":").append("\n\n");
            stringBuilder.append(responseSent).append("\n\n");
        }

        if ( response > 0)
        {
            stringBuilder.append("Server responded with: ").append(response);
        }

        return stringBuilder.toString();

    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRequestContent() {
        return requestContent;
    }

    public void setRequestContent(String requestContent) {
        this.requestContent = requestContent;
    }

    public String getResponseSent() {
        return responseSent;
    }

    public void setResponseSent(String responseSent) {
        this.responseSent = responseSent;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public String getResponseURL() {
        return responseURL;
    }

    public void setResponseURL(String responseURL) {
        this.responseURL = responseURL;
    }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }
}
