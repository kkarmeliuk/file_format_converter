package org.example.processors;

import org.apache.commons.io.FilenameUtils;
import org.example.converters.CSVFileConverter;
import org.example.converters.FileConverter;
import org.example.converters.JSONFileConverter;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * FileProcessor manages the multithreaded processing of files.
 * It reads files, creates threads for conversion, call converters for next file conversions.
 */
public class FileProcessor {
    private FileConverter converter;

    /**
     * @param sourceDirectory contains a path to directory with src files
     * @param destinationDirectory contains a path to directory for dest files
     */
    public void processFiles(String sourceDirectory, String destinationDirectory) {
        // Get src files to process
        File[] csvFiles = getSrcFilesByExtension(sourceDirectory, FileConverter.FileFormatType.CSV);
        File[] jsonFiles = getSrcFilesByExtension(sourceDirectory, FileConverter.FileFormatType.JSON);

        // Define the required number of threads for concurrency processing
        int cores = Runtime.getRuntime().availableProcessors();
        int numThreads = Math.min(csvFiles.length + jsonFiles.length, cores);

        // Execute processing of each file in a separate thread if possible.
        // Note: It's used for demo purpose, this approach is better for processing large files.
        try (ExecutorService threadPool = Executors.newFixedThreadPool(numThreads)) {

            // This implementation provides concurrency processing of files inside arrays,
            // but tracing of these two arrays happens one after the other.
            // It's also possible to run passFilesToConverter() methods in the separate threads.

            // Early check of CSV files existence, just to prevent unnecessary method call
            if (csvFiles.length > 0) {
                passFilesToConverter(
                        threadPool,
                        csvFiles,
                        destinationDirectory,
                        FileConverter.ConverterType.CSVtoJSON);
            }
            // Early check of JSON files existence, just to prevent unnecessary method call
            if (jsonFiles.length > 0) {
                passFilesToConverter(
                        threadPool,
                        jsonFiles,
                        destinationDirectory,
                        FileConverter.ConverterType.JSONtoCSV);
            }

            threadPool.shutdown();
        }
    }

    /**
     * @param threadPool contains reference to ExecutorService object
     * @param files contains reference to array of files
     * @param destinationDirectory contains path to dest directory
     * @param converterType contains a specified converter type
     */
    void passFilesToConverter(ExecutorService threadPool,
                              File[] files,
                              String destinationDirectory,
                              FileConverter.ConverterType converterType) {
        for (File file : files) {
            threadPool.execute(() -> {
                String sourceFilePath = file.getAbsolutePath();
                String fileName = file.getName();
                String srcFileExtension = FilenameUtils.getExtension(fileName);

                String destinationFilePath = destinationDirectory + File.separator +
                        fileName.replace("." + srcFileExtension, "." + converterType.getDestFileExtension());

                if(converterType == FileConverter.ConverterType.CSVtoJSON) {
                    converter = new CSVFileConverter();
                } else {
                    converter = new JSONFileConverter();
                }
                converter.convert(sourceFilePath, destinationFilePath);
            });
        }
    }

    /**
     * @param sourceDirectory contains a path to directory with src files
     * @param formatType contains file format value for filter
     * @return array of File objects (filtered by specified format)
     */
    private File[] getSrcFilesByExtension(String sourceDirectory, FileConverter.FileFormatType formatType) {
        File directory = new File(sourceDirectory);
        return directory.listFiles((dir, name) -> name.endsWith("." + formatType.getLabel()));
    }
}
