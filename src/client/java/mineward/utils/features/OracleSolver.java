package mineward.utils.features;

import config.practical.manager.ConfigValue;
import mineward.utils.utils.location.Location;
import mineward.utils.utils.location.Locations;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OracleSolver {

    private static final String ORACLE_QUESTION_TEXT = "equal to ";
    private static final String ORACLE_CORRECT_TEXT = "Correct!";
    private static final String ORACLE_FAIL_TEXT = "Bazinga!";

    private static final Pattern pattern = Pattern.compile("-?\\d+");

    private static final Vec3d GREEN_POS = new Vec3d(78.5, -52.25, -74.5);
    private static final Vec3d BLUE_POS = new Vec3d(74.5, -52.25, -74.5);
    private static final Vec3d RED_POS = new Vec3d(70.5, -52.25, -74.5);

    private static final double RENDER_SIZE = 0.5;

    @ConfigValue
    public static boolean solveOracle = true;

    @ConfigValue
    public static int answerColor = 0xff00ff00;

    public static Formatting currentFormating = Formatting.WHITE;
    public static Box currentBox = Box.of(GREEN_POS, RENDER_SIZE, RENDER_SIZE, RENDER_SIZE);

    public static boolean activeQuestion = false;

    public static void parse(Text message) {

        if (!Location.inLocation(Locations.ANVAHAR_TOMB) || !solveOracle) {
            activeQuestion = false;
            return;
        }

        String str = message.getString();

        if (str.contains(ORACLE_QUESTION_TEXT)) {
            solve(message);
        } else if(str.contains(ORACLE_CORRECT_TEXT) || str.contains(ORACLE_FAIL_TEXT)) {
            activeQuestion = false;
        }


    }

    public static void solve(Text message) {
        Matcher matcher = pattern.matcher(message.getString());
        ArrayList<Integer> nums = new ArrayList<>(6);

        try {
            while (matcher.find()) {
                nums.add(Integer.parseInt(matcher.group()));
            }
        } catch (NumberFormatException e) {
            return;
        }

        if (nums.size() != 6) return;
        int ans = nums.get(0) * nums.get(1) + nums.get(2);
        int green = nums.get(3);
        int blue = nums.get(4);
        int red = nums.get(5);

        MinecraftClient client = MinecraftClient.getInstance();

        updateFormatAndBox(ans, green, blue, red);
        activeQuestion = true;

        client.inGameHud.getChatHud()
                .addMessage(Text.literal("Answer is: ").setStyle(Style.EMPTY.withColor(Formatting.WHITE))
                        .append(Text.literal(ans + "").setStyle(Style.EMPTY.withColor(currentFormating))));
    }

    private static void updateFormatAndBox(int ans, int green, int blue, int red) {
        if (ans == green) {
            currentFormating = Formatting.GREEN;
            currentBox = Box.of(GREEN_POS, RENDER_SIZE, RENDER_SIZE, RENDER_SIZE);
        } else if (ans == blue) {
            currentFormating = Formatting.AQUA;
            currentBox = Box.of(BLUE_POS, RENDER_SIZE, RENDER_SIZE, RENDER_SIZE);
        } else if (ans == red) {
            currentFormating = Formatting.RED;
            currentBox = Box.of(RED_POS, RENDER_SIZE, RENDER_SIZE, RENDER_SIZE);
        } else {
            currentFormating = Formatting.WHITE;
            //Yeah, I don't know what position to assume
        }
    }


}
