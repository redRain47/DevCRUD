package ua.redrain47.hw11.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IOUtil {
    private String filePath;

    public IOUtil(String filePath) {
        this.filePath = filePath;
    }

    public boolean isEmpty() {
        File file = new File(filePath);
        return file.length() == 0;
    }

    public void save(String record) {
        try (FileWriter fileWriter = new FileWriter(filePath, true)) {
            record += "\n";
            fileWriter.append(record);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeAll(List<String> records) {
        clear();

        try (FileWriter fileWriter = new FileWriter(filePath, true)) {
            for (String record : records) {
                fileWriter.append(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> readAll() {
        ArrayList<String> records = new ArrayList<>();

        try (FileReader fileReader = new FileReader(filePath);
             Scanner scanner = new Scanner(fileReader)) {
            while (scanner.hasNext()) {
                records.add(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (records.size() != 0) ? records : null;
    }

    public void clear() {
        try (FileWriter fileWriter = new FileWriter(filePath, false)) {
            fileWriter.append("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
