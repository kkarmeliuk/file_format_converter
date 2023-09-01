package org.example.converters;

import org.example.utils.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * CSVFileConverter provides the logic for converting CSV files to JSON format.
 * It also uses FileUtils method to write the formatted data to files.
 */
public class CSVFileConverter implements FileConverter {

    /**
     * this implementation converts CSV file to JSON file
     * @param sourceFilePath      contains a path to src directory with CSV files
     * @param destinationFilePath contains a path to dest directory for JSON files
     */
    @Override
    public void convert(String sourceFilePath, String destinationFilePath) {
        try {
            List<String[]> csvData = FileUtils.readCSVFile(sourceFilePath);

            JSONArray jsonArray = new JSONArray();
            String[] headers = csvData.get(0);

            // Note: JSONObject is an unordered set of name/value pairs.
            // To preserve order of key-value pairs while converting
            // from CSV to JSON this code needs some changes.
            // TODO: apply Jackson lib to use Ordered JSON Object

            for (int i = 1; i < csvData.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                String[] values = csvData.get(i);

                for (int j = 0; j < headers.length; j++) {
                    jsonObject.put(headers[j], values[j]);
                }

                jsonArray.put(jsonObject);
            }

            FileUtils.writeJSONFile(jsonArray.toString(), destinationFilePath);
            System.out.println("Conversion completed for " + sourceFilePath);

        }
        catch (IOException e) {
            System.out.println("Error converting CSV to JSON: " + e.getMessage());
        }
    }
}
