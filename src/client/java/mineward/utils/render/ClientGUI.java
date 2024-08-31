package mineward.utils.render;

import java.text.DecimalFormat;
import java.util.ArrayList;

import mineward.utils.DamageTracking;
import mineward.utils.Dimension;
import mineward.utils.GwonkleHelper;
import mineward.utils.LottbsTimer;
import mineward.utils.Waypoint;
import mineward.utils.utils;
import mineward.utils.config.Config;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

public abstract class ClientGUI {

    public static void register() {
        HudRenderCallback.EVENT.register((context, tickDelta) -> {

            if (Dimension.inDimension("overworld")) {
                renderDamageTracking(context);
            }

            if (Config.displayWaypoints && Dimension.inDimension("oceanarea")) {
                renderWaypointGUI(context);
            }
            if (Config.displayCooldowns) {
                renderAbilities(context, tickDelta);
            }
            if (Config.displayPickupCount) {
                renderPickupCount(context);
            }

            if (Dimension.inDimension("hyrrill")) {
                renderLottbsTimer(context);
            }

        });

    }

    public static void renderDamageTracking(DrawContext context) {
        LivingEntity livingEntity = DamageTracking.getEntity();
        MinecraftClient client = utils.getClient();

        if (livingEntity != null && utils.inRange(client.player.getPos(), new Vec3d(1128, 65, 1006), 100)) {
            context.drawText(client.textRenderer, DamageTracking.getDps() + "/s", 150,
                    40,
                    0xffffff,
                    true);
        }
    }

    /**
     * Renders the waypoint gui
     * 
     * @param context DrawContext
     */
    public static void renderWaypointGUI(DrawContext context) {
        ArrayList<Waypoint> waypoints = GwonkleHelper.getWaypoints();
        MinecraftClient client = utils.getClient();
        context.drawText(client.textRenderer, GwonkleHelper.getWaypoints().size() + " locations left", 10, 10,
                0xffffff,
                true);
        for (int i = 0; i < waypoints.size() && i < 5; i++) {
            context.drawText(client.textRenderer, waypoints.get(i).getName() + "", 10, 20 + 10 * i,
                    0xffffff, true);
        }
    }

    public static int AbilityRenderHeight = 20;

    /**
     * Renders ability cooldowns
     * 
     * @param context
     * @param tickDelta
     */
    public static void renderAbilities(DrawContext context, double tickDelta) {
        AbilityRenderHeight = 80;
        MinecraftClient client = utils.getClient();

        try {
            if (Cooldown.activeCooldowns.isEmpty()) {
                return;
            }

            Cooldown.activeCooldowns.forEach((ability, name) -> {

                String cd = new DecimalFormat("###.#").format((ability.getCooldown() - ability.getTime()));
                String dur = new DecimalFormat("###.#").format((ability.getDuration() - ability.getTime()));

                context.fill(17, AbilityRenderHeight - 1, 124, AbilityRenderHeight + 23, 0x10FFFFFF);

                if (ability.getTime() < ability.getDuration()) {
                    // when ability is active
                    int length = (int) ((120 - 20) * (1 - ability.getTime() / ability.getDuration())) + 20;
                    context.fill(20, AbilityRenderHeight + 10, length, AbilityRenderHeight + 21, ability.hexColor);
                    context.drawText(client.textRenderer, dur + "s", 60, AbilityRenderHeight + 12, 0xFF000000, false);
                } else {
                    // the cooldown
                    int length = (int) ((120 - 20) * (1 - (ability.getTime() - ability.getDuration())
                            / (ability.getCooldown() - ability.getDuration()))) + 20;
                    context.fill(20, AbilityRenderHeight + 10, length, AbilityRenderHeight + 21, ability.hexColor);
                    context.drawText(client.textRenderer, cd + "s", 60, AbilityRenderHeight + 12, 0xFF000000, false);

                }

                context.drawText(client.textRenderer, name, 19, AbilityRenderHeight, 0xFF070707, false);
                context.drawBorder(20, AbilityRenderHeight + 10, 100, 11, 0xFF000000);

                AbilityRenderHeight += 24;
            });
        } catch (Exception e) {
        }
    }

    /**
     * Renders a counter on how many pickups youve done intotal
     * 
     * @param context DrawContext
     */
    public static void renderPickupCount(DrawContext context) {
        MinecraftClient client = utils.getClient();
        if (client.world.getRegistryKey().getValue().toString().contains("anvahar")) {
            context.drawText(client.textRenderer, "Pickups: " + Config.pickupCount, 70, 240, 0xffffffff, true);
        }
    }

    public static void renderLottbsTimer(DrawContext context) {
        float time = LottbsTimer.getTime();
        if (time <= 0) {
            return;
        }

        String str = new DecimalFormat("###.#").format(time) + "s";
        MinecraftClient client = utils.getClient();
        int length = (int) (100 * (10 / 10)) + 370;

        context.fill(370, 70 + 10, length, 70 + 21, 0xffee2222);
        context.drawText(client.textRenderer, str, 370 + 45, 70 + 12, 0xFF070707, false);
        context.drawBorder(370, 70 + 10, 100, 11, 0xFF000000);
    }
}
