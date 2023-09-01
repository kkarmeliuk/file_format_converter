package org.example.converters;
/**
 * FileConverter defines the contract for all file format converters.
 */
public interface FileConverter {
    /**
     * method realizes logic of file format conversion
     * @param sourceFilePath contains a path to directory with src files
     * @param destinationFilePath contains a path to directory for dest files
     */
    void convert(String sourceFilePath, String destinationFilePath);

    /**
     * Supported file format types for conversion
     */
    enum FileFormatType {
        CSV("csv"),
        JSON("json");

        // Use labels for encapsulation of such chain of calls like
        // FileConverter.FileFormatType.CSV.name().toLowerCase() in external classes
        // ***
        // Note: Better level of encapsulation could be achieved with using abstract class
        // instead of interface for FileConverter, to provide static method implementation for getting labels
        private final String label;

        FileFormatType(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Available Converter types
     * Need for easy support for a possible increase in the number of formats
     */
    enum ConverterType {
        CSVtoJSON("json"),
        JSONtoCSV("csv");
        private final String destFileExtension;

        /**
         * @param destFileExtension contains converted format name
         */
        ConverterType(String destFileExtension) {
            this.destFileExtension = destFileExtension;
        }

        public String getDestFileExtension() {
            return destFileExtension;
        }
    }
}
