package utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {

    public static void appendLine(String filePath, String line) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filePath, true), StandardCharsets.UTF_8))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("❌ CSV append error: " + e.getMessage());
        }
    }

    public static void writeAll(String filePath, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filePath, false), StandardCharsets.UTF_8))) {

            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("❌ CSV write error: " + e.getMessage());
        }
    }

    public static List<String[]> readAll(String filePath) {
        List<String[]> rows = new ArrayList<>();

        File file = new File(filePath);
        if (!file.exists()) {
            return rows;
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                rows.add(data);
            }

        } catch (IOException e) {
            System.out.println("❌ CSV read error: " + e.getMessage());
        }

        return rows;
    }
}
