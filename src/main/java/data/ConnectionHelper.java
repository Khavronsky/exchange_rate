package data;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * класс создает соединение с сервером и имеет метод для получения данных c сервера
 */

public class ConnectionHelper {
    private HttpsURLConnection connection = null;

    private boolean getConnection(String path) {

        try {
            URL url = new URL(path);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.connect();
        } catch (Exception e) {
            System.out.println("Could not connect to server");
            closeConnection();
            return false;
        }
        return true;
    }

    private void closeConnection() {
        if (connection != null) {
            connection.disconnect();
        }
    }

    public String getData(String path) throws IOException {
        BufferedReader reader = null;
        if (getConnection(path)) {
            try {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder buf = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    buf.append(line + "\n");
                }
                return (buf.toString());
            } finally {
                if (reader != null) {
                    reader.close();
                }
                closeConnection();
            }
        }
        return "Failed to load data";
    }

}
