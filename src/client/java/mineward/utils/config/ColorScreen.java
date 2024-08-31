package mineward.utils.config;

import java.text.DecimalFormat;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper.Argb;

public class ColorScreen extends Screen {
    Screen parent;

    protected ColorScreen(Screen parent) {
        super(Text.literal("Color screen"));
        this.parent = parent;
    }

    private static int buttonWidth = 200;
    DecimalFormat format = new DecimalFormat("0.00");

    @Override
    protected void init() {

        buttonWidth = width / 4 - 5;

        SliderWidget redP = new SliderWidget(width / 4 - 10, 25, buttonWidth / 3, 20,
                Text.literal("Red: " + format.format(Config.pickupRed)), Config.pickupRed) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.literal("Red: " + format.format(this.value)));
            }

            @Override
            protected void applyValue() {
                Config.pickupRed = (float) (this.value);
            }
        };
        SliderWidget greenP = new SliderWidget(width / 4 - 10, 50, buttonWidth / 3, 20,
                Text.literal("green: " + format.format(Config.pickupGreen)), Config.pickupGreen) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.literal("green: " + format.format(this.value)));
            }

            @Override
            protected void applyValue() {
                Config.pickupGreen = (float) (this.value);
            }
        };
        SliderWidget blueP = new SliderWidget(width / 4 - 10, 75, buttonWidth / 3, 20,
                Text.literal("blue: " + format.format(Config.pickupBlue)), Config.pickupBlue) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.literal("blue: " + format.format(this.value)));
            }

            @Override
            protected void applyValue() {
                Config.pickupBlue = (float) (this.value);
            }
        };

        SliderWidget redT = new SliderWidget(width / 2 - 10, 25, buttonWidth / 3, 20,
                Text.literal("Red: " + format.format(Config.treasureRed)), Config.treasureRed) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.literal("Red: " + format.format(this.value)));
            }

            @Override
            protected void applyValue() {
                Config.treasureRed = (float) (this.value);
            }
        };
        SliderWidget greenT = new SliderWidget(width / 2 - 10, 50, buttonWidth / 3, 20,
                Text.literal("green: " + format.format(Config.treasureGreen)), Config.treasureGreen) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.literal("green: " + format.format(this.value)));
            }

            @Override
            protected void applyValue() {
                Config.treasureGreen = (float) (this.value);
            }
        };
        SliderWidget blueT = new SliderWidget(width / 2 - 10, 75, buttonWidth / 3, 20,
                Text.literal("blue: " + format.format(Config.treasureBlue)), Config.treasureBlue) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.literal("blue: " + format.format(this.value)));
            }

            @Override
            protected void applyValue() {
                Config.treasureBlue = (float) (this.value);
            }
        };

        addDrawableChild(redP);
        addDrawableChild(greenP);
        addDrawableChild(blueP);
        addDrawableChild(redT);
        addDrawableChild(greenT);
        addDrawableChild(blueT);

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        //pickups
        context.drawText(textRenderer, Text.literal("Pickup color"), width / 4 - 5, 10, 0xffffffff, true);
        context.fill(width / 4 + buttonWidth / 3, 35, width / 4 + buttonWidth / 3 + 50, 85,
                Argb.getArgb(255, (int) (Config.pickupRed * 255), (int) (Config.pickupGreen * 255),
                        (int) (Config.pickupBlue * 255)));

        //treasure
        context.drawText(textRenderer, Text.literal("Treasure color"), width / 2 - 5, 10, 0xffffffff, true);
        context.fill(width / 2 + buttonWidth / 3, 35, width / 2 + buttonWidth / 3 + 50, 85,
                Argb.getArgb(255, (int) (Config.treasureRed * 255), (int) (Config.treasureGreen * 255),
                        (int) (Config.treasureBlue * 255)));

    }

    @Override
    public void close() {
        client.setScreen(parent);
        Config.set("pickupRed", Config.pickupRed);
        Config.set("pickupGreen", Config.pickupGreen);
        Config.set("pickupBlue", Config.pickupBlue);
        Config.set("treasureRed", Config.treasureRed);
        Config.set("treasureGreen", Config.treasureGreen);
        Config.set("treasureBlue", Config.treasureBlue);

    }
}
