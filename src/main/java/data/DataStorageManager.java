package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

/**
 * сохраняет данные на диск имеет методы чтения и записи данных
 */
public class DataStorageManager {

    private static final String FILE_PATH = "src/main/resources/saved_rate.txt";

    public boolean checkActualData() {
        synchronized (this) {

            File file = new File(FILE_PATH);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            long date = calendar.getTimeInMillis();
            try {
                Scanner scanner = new Scanner(file);
                if (scanner.hasNextLine() && date < Long.parseLong(scanner.nextLine())) {
                    return true;
                } else {
                    saveData(String.valueOf(System.currentTimeMillis()), false);
                    return false;
                }
            } catch (Exception e) {
                saveData(String.valueOf(System.currentTimeMillis()), false);
                return false;
            }
        }
    }

    public boolean saveData(String data, boolean append) {
        synchronized (this) {

            File file = new File(FILE_PATH);
            FileWriter printWriter = null;
            try {
                if (file.createNewFile()) {
                    append = false;
                }
                printWriter = new FileWriter(file, append);
                printWriter.write(data + "\n");
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                if (printWriter != null) {
                    try {
                        printWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return true;
        }
    }

    public String getDataFromFile(String requestedData) {
        synchronized (this) {

            File file = new File(FILE_PATH);
            String response = "";
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    response = scanner.nextLine();
                    if (response.contains(requestedData)) {
                        return response;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return "";
        }
    }
}
