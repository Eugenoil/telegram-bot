import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

public class Connections {

    private String dataSourceUrl = "";
    private String dataSourceKey = "";
    private ServerSocket serverSocket = null;
    private Socket socket;
    private long propertiesUpdated;
    private final File appConfigPath;

    public Connections(String dataSourceKey) throws IOException {
//        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        appConfigPath = new File( "src/main/resources/app.properties");
        updateSettings(dataSourceKey);
    }

    private void updateSettings(String dataSourceKey) throws IOException {
        if (appConfigPath.exists()) {
            Properties appProps = new Properties();
            appProps.load(new FileInputStream(appConfigPath));
            this.dataSourceUrl = appProps.getProperty(dataSourceKey);
            this.dataSourceKey = dataSourceKey;
            int port = Integer.parseInt(appProps.getProperty("port"));
            if (serverSocket != null)
                serverSocket.close();
            serverSocket = new ServerSocket(port);
            propertiesUpdated = appConfigPath.lastModified();
        }
    }

    public String listen() throws IOException {
        if (appConfigPath.lastModified() != propertiesUpdated)
            updateSettings(this.dataSourceKey);
        socket = serverSocket.accept();
        InputStreamReader isr = new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8);
        BufferedReader input = new BufferedReader(isr);
        while (!input.ready()) ;
        return input.readLine(); //Request first line only
    }

    public void sendResponse(String contents) throws IOException {
        PrintWriter output = new PrintWriter(socket.getOutputStream());
        output.println("HTTP/1.1 200 OK");
        output.println("Content-Type: text/html; charset=utf-8");
        output.println();
        output.println(contents);
        output.flush();
        socket.close();
    }

    public String sendRequest(String request, String contents) throws IOException {
        URL url = new URL(dataSourceUrl + request);
        BufferedReader input = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = input.readLine()) != null)
            sb.append(line).append("\n");
        return sb.toString();
    }
}
