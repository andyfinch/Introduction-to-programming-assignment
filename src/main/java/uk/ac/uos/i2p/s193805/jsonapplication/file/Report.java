package uk.ac.uos.i2p.s193805.jsonapplication.file;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 24/12/2018
 * Time: 09:00
 */

/**
 * Implementation of FileWritable interface define fileName and content for JSON and HTTP request/responses
 */
public class Report implements FileWritable {

    private static final String BASE_PATH = "src/Results/";

    private String fileName;
    private String requestContent;
    private String requestURL;
    private String responseURL;
    private String responseSent;
    private int response;


    /**
     * Uses BASE_PATH plus fileName property to build full path
     * @return The full path and filename
     */
    @Override
    public String getFileName() {
        return BASE_PATH+fileName;
    }

    /**
     * Uses StringBuilder to build content for JSON and HTTP request/responses
     * @return Content to be included on report
     */
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

    /**
     * 
     * @param fileName - Required filename
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     *
     * @return
     */
    public String getRequestContent() {
        return requestContent;
    }

    /**
     *
     * @param requestContent
     */
    public void setRequestContent(String requestContent) {
        this.requestContent = requestContent;
    }

    /**
     *
     * @return
     */
    public String getResponseSent() {
        return responseSent;
    }

    /**
     *
     * @param responseSent
     */
    public void setResponseSent(String responseSent) {
        this.responseSent = responseSent;
    }

    /**
     *
     * @return
     */
    public String getRequestURL() {
        return requestURL;
    }

    /**
     *
     * @param requestURL
     */
    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    /**
     *
     * @return
     */
    public String getResponseURL() {
        return responseURL;
    }

    /**
     *
     * @param responseURL
     */
    public void setResponseURL(String responseURL) {
        this.responseURL = responseURL;
    }

    /**
     *
     * @return
     */
    public int getResponse() {
        return response;
    }

    /**
     *
     * @param response
     */
    public void setResponse(int response) {
        this.response = response;
    }
}
