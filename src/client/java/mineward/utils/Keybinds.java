package mineward.utils;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class Keybinds {
    private static KeyBinding resetWaypoint;
    private static KeyBinding clearWaypoint;
    private static KeyBinding openConfig;

    public static void register() {
        resetWaypoint = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Reset Waypoints",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_U,
                "Mineward Utils"));

        clearWaypoint = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Clear Waypoints",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                "Mineward Utils"));

        openConfig = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Opens config menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                "Mineward Utils"));


    }

    /**
     * Checks inputs and executes if it meets any keybinds
     * @param client MinecraftClient
     */
    public static void checkInputs(MinecraftClient client){
        if (resetWaypoint.wasPressed()) {
            GwonkleHelper.resetWaypoints();
        }
        if (clearWaypoint.wasPressed()) {
            GwonkleHelper.waypoints.clear();
        }
        if (openConfig.wasPressed()) {
            MinecraftClient.getInstance().setScreen(new ConfigScreen());
        }
    }

}
