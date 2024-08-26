package mineward.utils;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;

import mineward.utils.config.Config;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;
public abstract class Commands {

    public static void register() {

        ClientCommandRegistrationCallback.EVENT
        .register((dispatcher, registryAccess) -> dispatcher
                .register(ClientCommandManager.literal("muHelp")
                        .executes(context -> {
                        context.getSource().sendFeedback(Text.literal("Available commands:"));
                        context.getSource().sendFeedback(Text.literal("/waypoints (reset/clear/render/display)"));
                        context.getSource().sendFeedback(Text.literal("reset: resets all waypoints"));
                        context.getSource().sendFeedback(Text.literal("clear: clears the waypoints list"));
                        context.getSource().sendFeedback(Text.literal("render: toggles waypoint rendering"));
                        context.getSource().sendFeedback(Text.literal("display: toggles the waypoint display\n"));
                        context.getSource().sendFeedback(Text.literal("/toggleHead: toggles the rendering of player heads\n"));
                        context.getSource().sendFeedback(Text.literal("/toggleCooldowns: toggles the display of cooldowns\n"));
                        context.getSource().sendFeedback(Text.literal("/togglePickups: toggles the rendering of pickups\n"));
                        context.getSource().sendFeedback(Text.literal("/setPickupColor (float) (float) (float): sets the color of the pickups\n"));
                        context.getSource().sendFeedback(Text.literal("/clearPickups: clears all pickups\n"));
                        context.getSource().sendFeedback(Text.literal("/resetTarget: Resets the targetdummy for the damage tracker to track\n"));
                        context.getSource().sendFeedback(Text.literal("/toggleTreasures: toggles the rendering of treasures\n"));
                        context.getSource().sendFeedback(Text.literal("/setTreasureColor (float) (float) (float): sets the color of treasures\n"));

                                return 1;
                        }))); 

        ClientCommandRegistrationCallback.EVENT
                .register((dispatcher, registryAccess) -> dispatcher
                        .register(ClientCommandManager.literal("waypoints")
                                .then(ClientCommandManager.argument("reset/clear/render/display", StringArgumentType.word()).executes(context -> {
                                        final String option = StringArgumentType.getString(context, "reset/clear/render/display");
                                        switch (option) {
                                                case "reset":
                                                        GwonkleHelper.resetWaypoints();
                                                        break;
                                                case "clear":
                                                        GwonkleHelper.waypoints.clear();
                                                        break;
                                                case "render":
                                                Config.renderWaypoints = !Config.renderWaypoints;
                                                        Config.set("renderWaypoints", Config.renderWaypoints);
                                                        break;
                                                case "display":
                                                        Config.displayWaypoints = !Config.displayWaypoints;
                                                        Config.set("displayWaypoints", Config.displayWaypoints);
                                                        break;
                                        }
                                        return 1;
                                }))));  
        ClientCommandRegistrationCallback.EVENT
                .register((dispatcher, registryAccess) -> dispatcher
                        .register(ClientCommandManager.literal("toggleHead")
                                .executes(context -> {
                                        Config.renderHead = !Config.renderHead;
                                        Config.set("displayHead", Config.renderHead);
                                        return 1;
                                }))); 
                                
        ClientCommandRegistrationCallback.EVENT
                .register((dispatcher, registryAccess) -> dispatcher
                        .register(ClientCommandManager.literal("toggleCooldowns")
                                .executes(context -> {
                                        Config.displayCooldowns = !Config.displayCooldowns;
                                        Config.set("displayCooldowns", Config.renderHead);
                                        return 1;
                                })));  

        ClientCommandRegistrationCallback.EVENT
                .register((dispatcher, registryAccess) -> dispatcher
                        .register(ClientCommandManager.literal("togglePickups")
                                .executes(context -> {
                                        Config.renderPickups = !Config.renderPickups;
                                        Config.set("renderPickups", Config.renderPickups);
                                        return 1;
                                })));  

        ClientCommandRegistrationCallback.EVENT
                .register((dispatcher, registryAccess) -> dispatcher
                        .register(ClientCommandManager.literal("setPickupColor")
                                .then(ClientCommandManager.argument("red", FloatArgumentType.floatArg())
                                        .then(ClientCommandManager.argument("green", FloatArgumentType.floatArg())
                                                .then(ClientCommandManager.argument("blue", FloatArgumentType.floatArg())
                                .executes(context -> {
                                        Config.pickupRed = FloatArgumentType.getFloat(context, "red");
                                        Config.set("pickupRed", Config.pickupRed);
                                        Config.pickupGreen = FloatArgumentType.getFloat(context, "green");
                                        Config.set("pickupGreen", Config.pickupGreen);
                                        Config.pickupBlue = FloatArgumentType.getFloat(context, "blue");
                                        Config.set("pickupBlue", Config.pickupBlue);
                                        return 1;
                                }))))));  

        ClientCommandRegistrationCallback.EVENT
                .register((dispatcher, registryAccess) -> dispatcher
                        .register(ClientCommandManager.literal("clearPickups")
                                .executes(context -> {
                                        PickupHighlight.clear();
                                        return 1;
                                })));  
                                
        ClientCommandRegistrationCallback.EVENT
                .register((dispatcher, registryAccess) -> dispatcher
                        .register(ClientCommandManager.literal("incPickup")
                                .executes(context -> {
                                        PickupHighlight.increasePickupCount();
                                        return 1;
                                })));  
                                
        ClientCommandRegistrationCallback.EVENT
                .register((dispatcher, registryAccess) -> dispatcher
                        .register(ClientCommandManager.literal("resetTarget")
                                .executes(context -> {
                                        DamageTracking.resetTarget();
                                        return 1;
                                }))); 

        ClientCommandRegistrationCallback.EVENT
                .register((dispatcher, registryAccess) -> dispatcher
                        .register(ClientCommandManager.literal("setTreasureColor")
                                .then(ClientCommandManager.argument("red", FloatArgumentType.floatArg())
                                        .then(ClientCommandManager.argument("green", FloatArgumentType.floatArg())
                                                .then(ClientCommandManager.argument("blue", FloatArgumentType.floatArg())
                                .executes(context -> {
                                        Config.treasureRed = FloatArgumentType.getFloat(context, "red");
                                        Config.set("treasureRed", Config.treasureRed);
                                        Config.treasureGreen = FloatArgumentType.getFloat(context, "green");
                                        Config.set("treasureGreen", Config.treasureGreen);
                                        Config.treasureBlue = FloatArgumentType.getFloat(context, "blue");
                                        Config.set("treasureBlue", Config.treasureBlue);
                                        return 1;
                                })))))); 
                                
        ClientCommandRegistrationCallback.EVENT
                .register((dispatcher, registryAccess) -> dispatcher
                        .register(ClientCommandManager.literal("toggleTreasures")
                                .executes(context -> {
                                        Config.renderTreasures = !Config.renderTreasures;
                                        Config.set("renderTreasures", Config.renderTreasures);
                                        return 1;
                                }))); 
                        }   
                        
                        
}
