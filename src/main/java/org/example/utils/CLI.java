package org.example.utils;

import org.apache.commons.cli.*;

/**
 * This CLI component uses a library like Apache Commons CLI to handle command-line
 * arguments and options. It provides users with clear instructions on how to use the utility.
 */
public class CLI {
    private CommandLine cmd;

    /**
     * @param args represented by user-supplied program options
     */
    public CLI(String[] args) {

        System.out.println("Welcome to The File Format Converter!\n" +
                "I provide bidirectional conversion between CSV and JSON file formats.");

        // Create options object to define the command-line options
        Options options = new Options();
        options.addOption("s", "source", true,
                "Source directory containing CSV/JSON files");
        options.addOption("d", "destination", true,
                "Destination directory for formatted JSON/CSV files");

        CommandLineParser parser = new DefaultParser();

        try {
            cmd = parser.parse(options, args);
        }
        catch (ParseException e) {
            System.out.println("Error parsing command-line arguments: " + e.getMessage());
        }
    }

    /**
     * @return path to src directory
     */
    public String getSourceDirectory() {
        return cmd.getOptionValue("s");
    }

    /**
     * @return path to dest directory
     */
    public String getDestinationDirectory() {
        return cmd.getOptionValue("d");
    }

    /**
     * @return result of checking the required options
     */
    public boolean isValid() {
        return cmd.hasOption("s") && cmd.hasOption("d");
    }

    /**
     * Simple print message method
     */
    public void printUsageRuleMessage(){
        System.out.println("Usage: -s <source_directory> -d <destination_directory>");
    }

    /**
     * Simple print message method
     */
    public void printFinalSuccessMessage() {
        System.out.println("Conversion completed successfully!");
    }
}
