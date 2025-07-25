package mineward.utils.utils.config;

import config.practical.ConfigSection;
import config.practical.ConfigurableScreen;
import config.practical.category.ConfigCategory;
import config.practical.manager.ConfigManager;
import config.practical.widgets.ConfigBool;
import config.practical.widgets.ConfigColor;
import mineward.utils.features.EntityHandler;
import mineward.utils.features.OracleSolver;
import mineward.utils.features.PickupHandler;
import mineward.utils.utils.Constants;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.List;

public class Config {

    private static final Text TITLE = Text.literal("Mineward Utilities");
    public static final ConfigManager manager = new ConfigManager("./config/" + Constants.NAMESPACE + ".json",
            List.of(PickupHandler.class, OracleSolver.class, EntityHandler.class));

    public static Screen createScreen() {
        ConfigurableScreen screen = new ConfigurableScreen(TITLE);

        ConfigCategory tombs = new ConfigCategory("Tombs");
        tombs.add(new ConfigBool(Text.literal("Highlight crystals"), manager, () -> EntityHandler.highlightCrystals, bool -> EntityHandler.highlightCrystals = bool));
        tombs.add(new ConfigColor(Text.literal("Crystal color"), manager, () -> EntityHandler.crystalColor, color -> EntityHandler.crystalColor = color));

        ConfigSection anvahar = new ConfigSection(Text.literal("Anvahar"));
        anvahar.add(new ConfigBool(Text.literal("Highlight pickups"), manager, () -> PickupHandler.highLightPickups, bool -> PickupHandler.highLightPickups = bool));
        anvahar.add(new ConfigColor(Text.literal("Pickup color"), manager, () -> PickupHandler.pickupColor, color -> PickupHandler.pickupColor = color));
        anvahar.add(new ConfigBool(Text.literal("Oracle solver"), manager, () -> OracleSolver.solveOracle, bool ->  OracleSolver.solveOracle = bool));
        anvahar.add(new ConfigColor(Text.literal("Oracle answer color"), manager, () -> OracleSolver.answerColor, color -> OracleSolver.answerColor = color));
        tombs.add(anvahar);

        ConfigSection ancient = new ConfigSection(Text.literal("Ancient"));
        ancient.add(new ConfigBool(Text.literal("Highlight chests"), manager, () -> EntityHandler.highlightChests, bool -> EntityHandler.highlightChests = bool));
        ancient.add(new ConfigColor(Text.literal("Chest color"), manager, () -> EntityHandler.chestColor, color -> EntityHandler.chestColor = color));
        tombs.add(ancient);

        screen.addCategory(tombs);

        return screen;
    }
}
