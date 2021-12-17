/*
 * This Groovy source file was generated by the Gradle "init" task.
 */
package AccentureSearch

import groovy.io.FileType
import java.nio.file.Files
import java.nio.file.Paths
import groovy.util.logging.Log

/**
 * Application for replacing text in files under a specified directory and its subdirectories
 */
@Log
class App {
    private String dir
    private String searchStr
    private String replaceTo

    def changedFiles = []
    String logFilePath

    /**
     * App class constructor
     *
     * @param dir - directory to process
     * @param searchStr - string to search
     * @param replaceTo - string to replace the search string
     */
    App(String dir, String searchStr, String replaceTo) {
        this.dir = dir
        this.searchStr = searchStr
        this.replaceTo = replaceTo
    }

    /**
     * Does search
     */
    void search() {
        changedFiles.clear()

        // Handling of recursive file search and check for content
        new File(dir).eachFileRecurse(FileType.FILES) { file ->
            if (file.text.contains(searchStr)) {
                log.info(file.name + " has been changed")
                file.text = file.text.replaceAll(searchStr, replaceTo)
                changedFiles.add("$file.parent $file.name")
            }
        }

        // Creating checked_files.txt with changed file names
        createLog()

        log.info("Search has been executed")
    }

    /**
     * Creates a file containing a list of changed files. File is created only if logFilePath class variable is set.
     * Log file is created if it doesn"t exist. If log file exists, information if appended there.
     */
    void createLog() {
        if (logFilePath != null) {
            def log = new File(logFilePath)
            if (!log.exists()) {
                log.createNewFile()
            }
            changedFiles.forEach(str -> log.append(str))
        }
    }

    /**
     * Validates input parameters
     * @param args - input parameters as it"s passed to the app
     * @return true if validation passed, false otherwise
     */
    static boolean validate(String... args) {
        // Check number of arguments. Maximum 4 is allowed.
        if (args.length < 3 || args.length > 4) {
            log.severe("Invalid number of arguments")
            return false
        }

        // Check path
        if (!Files.exists(Paths.get(args[0]))) {
            log.severe("Search directory doesn't exist")
            return false
        }

        return true
    }

    /**
     * Application entry point. Accepts 3 mandatory parameters:
     * 1. directory to process
     * 2. string to search in files
     * 3. string to replace the search string
     * One optional parameter can be passed defining a file name to write an information about changed files.
     * File is created if it doesn"t exist.
     *
     * @param args
     */
    static void main(String... args) {
        log.info("Initiated")
        if (!validate(args)) {
            log.severe("Validation error")
            System.exit(1)
        }

        App app = new App(args[0], args[1], args[2])

        // Set log file path if 4th parameter exists
        if (args.length > 3) {
            app.logFilePath = args[3]
        }

        app.search()
        app.createLog()
    }
}
