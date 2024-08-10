package mineward.utils.render;

import java.text.DecimalFormat;
import java.util.ArrayList;

import mineward.utils.Config;
import mineward.utils.GwonkleHelper;
import mineward.utils.Waypoint;
import mineward.utils.utils;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;


public class ClientGUI {
    public static void register() {
        HudRenderCallback.EVENT.register((context, tickDelta) -> {
            if (Config.displayWaypoints) {
                renderWaypointGUI(context);
            }
            if (Config.displayCooldowns) {
                renderAbilities(context, tickDelta);

            }
        });

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
                
                context.fill(17, AbilityRenderHeight-1, 124, AbilityRenderHeight + 23, 0x10FFFFFF);

                if (ability.getTime() < ability.getDuration()) {
                    //when ability is active
                    int length = (int)((120-20)*(1-ability.getTime()/ability.getDuration()))+20;
                    context.fill(20, AbilityRenderHeight + 10, length, AbilityRenderHeight + 21, ability.hexColor);
                    context.drawText(client.textRenderer, dur + "s", 60, AbilityRenderHeight + 12, 0xFF000000, false);
                } else {
                    //the cooldown
                    int length = (int)((120-20)*(1-(ability.getTime()-ability.getDuration())/(ability.getCooldown()-ability.getDuration())))+20;
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
}
