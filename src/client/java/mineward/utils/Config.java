package mineward.utils;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Files;

import org.slf4j.Logger;

public class Config {

    public static final Logger LOGGER = utils.getLogger();
    static File config = new File("./config/mineward_utils.txt");

    public static boolean renderHead = true;
    public static boolean renderWaypoints = false;
    public static boolean displayWaypoints = false;
    public static boolean renderPickups = false;
    public static boolean displayCooldowns = true;

    /**
     * Registers config
     */
    public static void register() {
        if (!config.exists()) {
            createConfigFile();
        }
        readConfigFile();
    }

    /**
     * Creates the config file if it dosent exist
     */
    private static void createConfigFile() {
        try {
            config.createNewFile();
        } catch (IOException err) {
            LOGGER.info("cant create file");
        }
    }

    /**
     * reads the config file and updates values
     */
    private static void readConfigFile() {
        renderWaypoints = searchBool("renderWaypoints", false);
        displayWaypoints = searchBool("displayWaypoints", false);
        renderHead = searchBool("renderHead", true);
        renderPickups = searchBool("renderPickups", true);
        displayCooldowns = searchBool("displayCooldowns", true);
    }

    /**
     * Searches for if that boolean exists in the config file, if not add it and set
     * to default value
     * 
     * @param string       String to search
     * @param defaultValue Boolean, default value
     * @param refrence     Boolean the reference to change
     */
    private static boolean searchBool(String string, Boolean defaultValue) {
        String parsed = getString(string);
        System.out.println(parsed + " " + string);
        if (parsed != null) {
            return parseBool(parsed);
        } else {
            addBool(string, defaultValue);
            return defaultValue;
        }
    }

    /**
     * Adds a boolean row with the inputed string + inputed value
     * 
     * @param string String to add
     * @param value  Value to set it to
     */
    private static void addBool(String string, boolean value) {

        try {
            List<String> lines = Files.readAllLines(config.toPath());
            lines.add(string + ":" + value);
            Files.write(config.toPath(), lines);
        } catch (Exception err) {

        }

    }

    /**
     * Returns the string that matches
     * 
     * @param string String
     * @return the string in config file
     */
    public static String getString(String string) {
        try {
            Scanner scanner = new Scanner(config);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(string)) {
                    scanner.close();
                    return line;
                }
            }
            scanner.close();
            return null;
        } catch (FileNotFoundException err) {
            LOGGER.info("failed to create scanner");
            return null;
        }
    }

    /**
     * Takes in a string and returns the setting
     * 
     * @param string
     * @return True if true, false otherwise
     */
    private static boolean parseBool(String string) {
        int index = string.indexOf(":");
        String parsed = string.substring(index + 1);
        if (parsed.contains("true")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Takes in a string to change its boolean on
     * 
     * @param string String the string to change
     * @param value  The new boolean value
     */
    public static void setBool(String string, boolean value) {
        try {
            List<String> lines = Files.readAllLines(config.toPath());
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).contains(string)) {
                    lines.set(i, string + ":" + value);
                }
            }
            Files.write(config.toPath(), lines);
        } catch (Exception err) {

        }
    }
}
