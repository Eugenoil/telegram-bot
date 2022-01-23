package client.track;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpSender {
    String destUrl;
    int port;

    public HttpSender() {
    }

    private HttpData doSend(HttpData httpData) throws IOException {
        HttpData result = new HttpData();
        if (destUrl == null && httpData == null)
            return result;
        String request = "GET / HTTP/1.1";
        URL url;
        if (httpData != null) {
            url = new URL(httpData.getUrl());
            request = httpData.mergeRequest(httpData);
        } else {
            url = new URL(destUrl);
        }
//This not working but better finish this part
//        URLConnection connection = url.openConnection();
//        connection.setDoOutput(true);
//        PrintWriter output = new PrintWriter(connection.getOutputStream());
//        output.println(request);
//        if (httpData != null && httpData.getHeaders().size() > 0)
//            for (String s : httpData.getHeaders())
//                output.println(s);
//        output.println();
//        if (httpData != null)
//            output.println(httpData.getBody());
//        output.flush();
        InputStreamReader isr = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8);
        BufferedReader input = new BufferedReader(isr);
        result.readData(input);
        return result;
    }

    public HttpData sendRequest(String request) throws IOException {
        this.destUrl = request;
        return doSend(null);
    }

    /***
     * Method to connect to any Server waiting for client connection (for ex. some destUrl) and send to it
     * HttpData. After connection method get data from server and return it as new HttpData
     * @param httpData request as HttpData to send
     * @return httpData response from destination
     * @throws IOException
     */
    public HttpData sendRequest(HttpData httpData) throws IOException {
        return doSend(httpData);
    }

    public String getdestUrl() {
        return destUrl;
    }

    public void setdestUrl(String destUrl) {
        this.destUrl = destUrl;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
