package mineward.utils.config;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Files;

import org.slf4j.Logger;

import mineward.utils.utils;

public abstract class Config {

    public static final Logger LOGGER = utils.getLogger();
    static File config = new File("./config/mineward_utils.txt");

    public static boolean renderHead = true;
    public static boolean renderWaypoints = false;
    public static boolean displayWaypoints = false;
    public static boolean renderPickups = false;
    public static boolean displayCooldowns = true;
    public static boolean displayPickupCount = true;
    public static boolean usePerformanceMode = true;
    public static boolean renderTreasures = true;

    public static int pickupCount = 0;

    public static float pickupRed = 0;
    public static float pickupGreen = 0;
    public static float pickupBlue = 0;

    public static float treasureRed = 0;
    public static float treasureGreen = 0;
    public static float treasureBlue = 0;

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
        displayPickupCount = searchBool("displayPickupCount", true);
        pickupCount = searchInt("pickupCount", 0);
        usePerformanceMode = searchBool("usePerformanceMode", true);
        renderTreasures = searchBool("renderTreasures", true);

        //pickup color
        pickupRed = searchFloat("pickupRed", 0);
        pickupGreen = searchFloat("pickupGreen", 0);
        pickupBlue = searchFloat("pickupBlue", 0);

        //chest color
        treasureRed = searchFloat("treasureRed", 0);
        treasureGreen = searchFloat("treasureGreen", 1);
        treasureBlue = searchFloat("treasureBlue", 0);
    }

    /**
     * Searches for if that boolean exists in the config file, if not add it and set
     * to default value
     * 
     * @param string       String to search
     * @param defaultValue Boolean, default value
     */
    private static boolean searchBool(String string, Boolean defaultValue) {
        String parsed = getString(string);
        if (parsed != null) {
            return parseBool(parsed);
        } else {
            add(string, defaultValue);
            return defaultValue;
        }
    }

    /**
     * Searches for if that int exists in the config file, if not add it and set
     * to default value
     * 
     * @param string       String to search
     * @param defaultValue int, default value
     */
    private static int searchInt(String string, int defaultValue) {
        String parsed = getString(string);
        if (parsed != null) {
            return parseInt(parsed);
        } else {
            add(string, defaultValue);
            return defaultValue;
        }
    }

    /**
     * Searches for if that int exists in the config file, if not add it and set
     * to default value
     * 
     * @param string       String to search
     * @param defaultValue int, default value
     */
    private static float searchFloat(String string, float defaultValue) {
        String parsed = getString(string);
        if (parsed != null) {
            return parseFloat(parsed, defaultValue);
        } else {
            add(string, defaultValue);
            return defaultValue;
        }
    }

    /**
     * Adds a boolean row with the inputed string + inputed value
     * 
     * @param string String to add
     * @param value  Value to set it to
     */
    private static void add(String string, Object value) {

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
     * Takes in a string and returns the setting for a boolean value
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
     * Takes in a string and returns the setting for an int value
     * 
     * @param string
     * @return The value if parsed, else 0
     */
    private static int parseInt(String string) {
        int index = string.indexOf(":");
        String parsed = string.substring(index + 1);
        try {
            return Integer.valueOf(parsed);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Takes in a string and returns the setting for a float value
     * 
     * @param string
     * @return The value if parse, else a default value
     */
    private static float parseFloat(String string, float defaultValue) {
        int index = string.indexOf(":");
        String parsed = string.substring(index + 1);
        try {
            return Float.valueOf(parsed);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Takes in a string to change its value on
     * 
     * @param string String the string to change
     * @param value  The new value
     */
    public static void set(String string, Object value) {
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
