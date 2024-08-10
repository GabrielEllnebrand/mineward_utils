package mineward.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class OracleSolver {
    public static void parseString(String string) {
        
        if (string.contains("equal to") && string.contains("Is")) {
            try {
                int answer = solve(string);
                MinecraftClient client = utils.getClient();
                client.inGameHud.getChatHud().addMessage(Text.literal("answer is " + answer));
            } catch (Exception e) {
                // failed to parse string
            }
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

}
