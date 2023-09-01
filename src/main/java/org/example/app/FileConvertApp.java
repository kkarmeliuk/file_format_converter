package org.example.app;

import org.example.utils.CLI;
import org.example.processors.FileProcessor;

/**
 * Project title: The File Format Converter

 * Overview: The File Format Converter is a simple command-line utility designed for bidirectional conversion
 * between CSV and JSON files. It utilizes respectively two types of format converters, multithreading
 * for processing multiple files concurrently, File I/O for reading and writing files.
 * This program was created to showcase application of basic SOLID principles of OO programming.

 * FileConverterApp class serves as the entry point for the application.
 * It parses command-line arguments (options), initializes the necessary components,
 * and coordinates the file conversion process.
 */

public class FileConvertApp {
    public static void main(String[] args) {
        // Create object to handle parsing and retrieving command-line arguments
        CLI cli = new CLI(args);

        // Check if required options are present
        if (!cli.isValid()) {
            cli.printUsageRuleMessage();
            return;
        }

        // Get src and dest directories' paths
        String sourceDirectory = cli.getSourceDirectory();
        String destinationDirectory = cli.getDestinationDirectory();

        // Create processor to execute formatting processes
        FileProcessor fileProcessor = new FileProcessor();
        fileProcessor.processFiles(sourceDirectory, destinationDirectory);

        cli.printFinalSuccessMessage();
    }
}