package org.example.utils;

import com.opencsv.CSVWriter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * FileUtils contains utility methods for reading and writing files using Java's File I/O classes.
 */
public class FileUtils {

    /**
     * @param filePath contains a path to file to read
     * @return list collection of string arrays
     */
    public static List<String[]> readCSVFile(String filePath) throws IOException {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                data.add(values);
            }
        }
        return data;
    }

    /**
     * @param jsonData contains JSON data
     * @param filePath contains path to file for writing
     */
    public static void writeJSONFile(String jsonData, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(jsonData);
        }
    }

    public static void writeCSVFile(String destinationFilePath,
                                    List<String> headers,
                                    JSONArray jsonArray) throws IOException {
        try (CSVWriter csvWriter = new CSVWriter(
                new FileWriter(destinationFilePath),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END)) {
            csvWriter.writeNext(headers.toArray(new String[0]));

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                List<String> values = new ArrayList<>();

                for (String header : headers) {
                    values.add(jsonObject.optString(header, ""));
                }

                csvWriter.writeNext(values.toArray(new String[0]));
            }
        }
    }

    /**
     * @param file contains reference to File object
     * @param charset contains reference to Charset object
     * @return string value with file content
     */
    public static String readFileToString(File file, Charset charset) throws IOException {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file, charset))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        }

        return content.toString();
    }

}
