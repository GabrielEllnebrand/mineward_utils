package mineward.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public abstract class OracleSolver {

    public static void parseString(String string) {
        try {
            int answer = solve(string);
            Formatting format = getFormat(string, answer);
            Text ans = Text.literal(answer + "").setStyle(Style.EMPTY.withColor(format));
            Text text = Text.literal("answer is ").setStyle(Style.EMPTY.withColor(Formatting.WHITE)).append(ans);
            MinecraftClient client = utils.getClient();

            client.inGameHud.getChatHud().addMessage(text);

        } catch (Exception e) {
            // failed to parse string
        }
    }

    /**
     * Solves the inputed message from oracle
     * 
     * @param string The oracles equation string
     * @return Int, the answer
     */
    public static int solve(String string) {
        int indexStart = string.indexOf("Is");
        int indexMul = string.indexOf("*");
        int index = string.indexOf("+");
        if (index == -1) {
            index = string.indexOf("-");
        }

        int indexEnd = string.substring(index).indexOf(" ") + string.length() - string.substring(index).length();

        int num1 = Integer.parseInt(string.substring(indexStart + 3, indexMul));
        int num2 = Integer.parseInt(string.substring(indexMul + 1, index));
        int num3 = Integer.parseInt(string.substring(index, indexEnd));

        return num1 * num2 + num3;

    }

    /**
     * Gets the format for the answer
     * @param string String
     * @param answer int
     * @return The answers color, if no match returns Formatting.WHITE
     */
    public static Formatting getFormat(String string, int answer) {

        int green = parseInt(string, "to", ",");
        int blue = parseInt(string, ",", "or");
        int red = parseInt(string, "or", "?");

        if (answer == green) {
            return Formatting.GREEN;
        } else if (answer == blue) {
            return Formatting.BLUE;
        } else if (answer == red) {
            return Formatting.RED;
        } else {
            return Formatting.WHITE;
        }
    }

    /**
     * Parses an int in a string from start string to end string
     * 
     * @param string String
     * @param start  String, the part of the string to start parsing
     * @param end    String, the part ofthe string to end parsing
     * @return  int, the int inbetween, if none returns 0;
     */
    public static int parseInt(String string, String start, String end) {
        try {
            int indexStart = string.indexOf(start) + start.length();
            int indexEnd = string.indexOf(end);
            String sub = string.substring(indexStart, indexEnd).trim();
            return Integer.parseInt(sub);
        } catch (Exception e) {

        }
        return 0;
    }

}
