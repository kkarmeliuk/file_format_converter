package org.example.converters;

import org.example.utils.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * JSONFileConverter provides the logic for converting JSON files to CSV format.
 * It also uses FileUtils method to write the formatted data to files.
 */
public class JSONFileConverter implements FileConverter {
    /**
     * this implementation converts JSON file to CSV file
     * @param sourceFilePath      contains a path to src directory with CSV files
     * @param destinationFilePath contains a path to dest directory for JSON files
     */
    @Override
    public void convert(String sourceFilePath, String destinationFilePath) {
        try {
            String jsonContent = FileUtils.readFileToString(new File(sourceFilePath), StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(jsonContent);

            if (jsonArray.isEmpty()) {
                System.out.println("No data in JSON file.");
                return;
            }

            JSONObject firstObject = jsonArray.getJSONObject(0);
            List<String> headers = new ArrayList<>(firstObject.keySet());

            FileUtils.writeCSVFile(destinationFilePath, headers, jsonArray);

            System.out.println("Conversion completed for " + sourceFilePath);
        }
        catch (IOException e) {
            System.out.println("Error converting JSON to CSV: " + e.getMessage());
        }
    }
}
