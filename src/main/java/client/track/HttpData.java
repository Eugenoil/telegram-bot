package client.track;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HttpData {
    private String httpMethod;
    private Map<String, String> parameters;
    private List<String> headers;
    private String body;
    private String url;

    public HttpData() {
        this.parameters = new HashMap<String, String>();
        this.headers = new LinkedList<String>();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void addHeader(String headerLine) {
        this.headers.add(headerLine);
    }
    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public void addParamenets(String key, String value) {
        key = URLEncoder.encode(key, StandardCharsets.UTF_8);
        value = URLEncoder.encode(value, StandardCharsets.UTF_8);
        this.parameters.put(key, value);
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /***
     * Read data from input BufferedReader and split it to request method, headers and body.
     * All of this put into curren object
     * @param input
     * @throws IOException
     */
    public void readData(BufferedReader input) throws IOException {
        String line = input.readLine();
        if (!line.equals("")) {
            String[] method = line.split(" ");
            setHttpMethod(method[0]);
            if (method.length > 1)
                setParameters(splitRequest(method[1]));
            addHeader(line);
            while (input.ready() && line != "") {
                line = input.readLine();
                addHeader(line);
            }
            StringBuilder sb = new StringBuilder();
            while (input.ready()) {
                line = input.readLine();
                sb.append(line);
            }
            setBody(sb.toString());
        }
    }

    public Map<String, String> splitRequest(String request) {
        Map<String, String> result = new HashMap<>();
        int startPos = request.indexOf("/");
        if (startPos < 0)
            startPos = request.indexOf("?");
        int endPos = request.lastIndexOf(" ");
        if (startPos < 0 || endPos < 0) {
            result.put("", "");
        } else {
            String[] paramsArray = request.split("&");
            for (String s : paramsArray) {
                String clearedParam = s.replaceAll("\\?", "");
                String[] keyValue = clearedParam.split("=");
                if (keyValue.length == 1)
                    result.put(keyValue[0], "");
                else
                    result.put(keyValue[0], keyValue[1]);
            }
        }
        return result;
    }

    public String mergeRequest(HttpData httpData) {
        StringBuilder sb = new StringBuilder();
        Set<String> keys = httpData.getParameters().keySet();
        int idx = 0;
        for (String key : keys) {
            String param = key + "=" + httpData.getParameters().get(key);
            if (idx < keys.size() - 1)
                param += "&";
            sb.append(param);
            idx++;
        }
        if (sb.length() == 0)
            return ("");
        return ("?" + sb);
    }

    @Override
    public String toString() {
        return "HttpData{" +
                "httpMethod='" + httpMethod + '\'' +
                ", parameters=" + parameters +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                '}';
    }
}