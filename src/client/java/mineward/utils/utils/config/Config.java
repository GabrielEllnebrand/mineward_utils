package mineward.utils.utils.config;

import config.practical.ConfigSection;
import config.practical.ConfigurableScreen;
import config.practical.category.ConfigCategory;
import config.practical.manager.ConfigManager;
import config.practical.widgets.ConfigBool;
import config.practical.widgets.ConfigColor;
import mineward.utils.features.EntityHandler;
import mineward.utils.features.HUDHandler;
import mineward.utils.features.OracleSolver;
import mineward.utils.features.PickupHandler;
import mineward.utils.utils.Constants;
import mineward.utils.utils.rendering.HUDComponents;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.List;

public class Config {

    private static final Text TITLE = Text.literal("Mineward Utilities");
    public static final ConfigManager manager = new ConfigManager("./config/" + Constants.NAMESPACE + ".json",
            List.of(PickupHandler.class, OracleSolver.class, EntityHandler.class, HUDHandler.class, HUDComponents.class));

    public static Screen createScreen(Screen parent) {
        ConfigurableScreen screen = new ConfigurableScreen(TITLE, parent, manager);

        ConfigCategory tombs = new ConfigCategory("Tombs");
        tombs.add(new ConfigBool(Text.literal("Highlight crystals"), () -> EntityHandler.highlightCrystals, bool -> EntityHandler.highlightCrystals = bool));
        tombs.add(new ConfigColor(Text.literal("Crystal color"), () -> EntityHandler.crystalColor, color -> EntityHandler.crystalColor = color));

        ConfigSection anvahar = new ConfigSection(Text.literal("Anvahar"));
        anvahar.add(new ConfigBool(Text.literal("Highlight pickups"), () -> PickupHandler.highLightPickups, bool -> PickupHandler.highLightPickups = bool));
        anvahar.add(new ConfigColor(Text.literal("Pickup color"), () -> PickupHandler.pickupColor, color -> PickupHandler.pickupColor = color));
        anvahar.add(new ConfigBool(Text.literal("Oracle solver"), () -> OracleSolver.solveOracle, bool ->  OracleSolver.solveOracle = bool));
        anvahar.add(new ConfigColor(Text.literal("Oracle answer color"), () -> OracleSolver.answerColor, color -> OracleSolver.answerColor = color));
        tombs.add(anvahar);

        ConfigSection ancient = new ConfigSection(Text.literal("Ancient"));
        ancient.add(new ConfigBool(Text.literal("Highlight chests"), () -> EntityHandler.highlightChests, bool -> EntityHandler.highlightChests = bool));
        ancient.add(new ConfigColor(Text.literal("Chest color"), () -> EntityHandler.chestColor, color -> EntityHandler.chestColor = color));
        tombs.add(ancient);

        screen.addCategory(tombs);

        ConfigCategory hud = new ConfigCategory("Hud");

        ConfigSection hide = new ConfigSection(Text.literal("Hide default components"));
        hide.add(new ConfigBool(Text.literal("Hide hunger"), () -> HUDHandler.hideHunger, bool -> HUDHandler.hideHunger = bool));
        hide.add(new ConfigBool(Text.literal("Hide armour"), () -> HUDHandler.hideArmourBar, bool -> HUDHandler.hideArmourBar = bool));
        hide.add(new ConfigBool(Text.literal("Hide hearts"), () -> HUDHandler.hideHearts, bool -> HUDHandler.hideHearts = bool));
        hide.add(new ConfigBool(Text.literal("Hide ActionBar"), () -> HUDHandler.hideActionBar, bool -> HUDHandler.hideActionBar = bool));
        hud.add(hide);

        ConfigSection render = new ConfigSection(Text.literal("Rendering"));
        render.add(new ConfigBool(Text.literal("Render health bar"), () -> HUDHandler.renderHealthBar, bool -> HUDHandler.renderHealthBar = bool));
        render.add(new ConfigBool(Text.literal("Render health number"), () -> HUDHandler.renderHealthNumber, bool -> HUDHandler.renderHealthNumber = bool));
        render.add(new ConfigBool(Text.literal("Render mana bar"), () -> HUDHandler.renderManaBar, bool -> HUDHandler.renderManaBar = bool));
        render.add(new ConfigBool(Text.literal("Render mana number"), () -> HUDHandler.renderManaNumber, bool -> HUDHandler.renderManaNumber = bool));
        hud.add(render);

        screen.addCategory(hud);

        return screen;
    }
}
