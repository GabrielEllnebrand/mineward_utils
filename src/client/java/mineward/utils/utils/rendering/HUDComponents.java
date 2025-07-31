package mineward.utils.utils.rendering;

import config.practical.hud.HUDComponent;
import config.practical.manager.ConfigValue;
import mineward.utils.features.HUDHandler;
import mineward.utils.features.PickupHandler;
import mineward.utils.utils.location.Location;
import mineward.utils.utils.location.Locations;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.util.Window;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class HUDComponents {

    @ConfigValue
    public static HUDComponent pickupCountComponent = new HUDComponent(0.1, 0.6, 70, 10, 1,
            () -> Location.inLocation(Locations.ANVAHAR_TOMB) && PickupHandler.highLightPickups,
            ((hudComponent, drawContext) -> {
                int x = hudComponent.getScaledX();
                int y = hudComponent.getScaledY();
                drawContext.drawText(MinecraftClient.getInstance().textRenderer, "Pickups: " + PickupHandler.getCount(), x, y, 0xffffffff, true);
            })
    );

    @ConfigValue
    public static HUDComponent healthNumber = new HUDComponent(0, 0.1, 100, 10, 1,
            () -> HUDHandler.renderHealthNumber,
            (hudComponent, drawContext) -> {
                int x = hudComponent.getScaledX();
                int y = hudComponent.getScaledY();

                TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
                Text text =  Text.literal(HUDHandler.currentHealth + "/" + HUDHandler.maxHealth).setStyle(Style.EMPTY.withFormatting(Formatting.RED));
                drawContext.drawText(renderer, text, x, y, 0xffffffff, true);

                hudComponent.setDimension(renderer.getWidth(text), renderer.fontHeight);
            });

    @ConfigValue
    public static HUDComponent healthBar = new HUDComponent(0, 0.15, 70, 10, 1,
            () -> HUDHandler.renderHealthBar,
            (hudComponent, drawContext) -> {
                int x = hudComponent.getScaledX();
                int y = hudComponent.getScaledY();
                int width = hudComponent.getWidth();
                int height = hudComponent.getHeight();

                double factor = calcPrecentage(HUDHandler.currentHealth, HUDHandler.maxHealth);


                drawContext.fill(x, y, x + (int)(width * factor), y + height, 0xffff0000);
                drawContext.drawBorder(x, y, width, height, 0xff000000);
            });

    @ConfigValue
    public static HUDComponent manaNumber = new HUDComponent(0, 0.2, 100, 10, 1,
            () -> HUDHandler.renderManaNumber,
            (hudComponent, drawContext) -> {
                int x = hudComponent.getScaledX();
                int y = hudComponent.getScaledY();

                TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
                Text text =  Text.literal(HUDHandler.currentMana + "/" + HUDHandler.maxMana).setStyle(Style.EMPTY.withFormatting(Formatting.BLUE));
                drawContext.drawText(renderer, text, x, y, 0xffffffff, true);

                hudComponent.setDimension(renderer.getWidth(text), renderer.fontHeight);
            });

    @ConfigValue
    public static HUDComponent manaBar = new HUDComponent(0, 0.25, 70, 10, 1,
            () -> HUDHandler.renderManaBar,
            (hudComponent, drawContext) -> {
                int x = hudComponent.getScaledX();
                int y = hudComponent.getScaledY();
                int width = hudComponent.getWidth();
                int height = hudComponent.getHeight();

                double factor = calcPrecentage(HUDHandler.currentMana, HUDHandler.maxMana);
                drawContext.fill(x, y, x + (int)(width * factor), y + height, 0xff0000ff);
                drawContext.drawBorder(x, y, width, height, 0xff000000);
            });

    public static double calcPrecentage(double curr, double max) {
        if (max <= 0) return 0;
        return Math.clamp(curr/max, 0, 1);
    }

    public static void load() {

    }
}
