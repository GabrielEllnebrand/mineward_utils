package mineward.utils.utils;

import mineward.utils.utils.config.Config;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class Keybinds {

    private static final String CATEGORY = Constants.NAMESPACE;

    private static KeyBinding openConfig;
    private static KeyBinding leave;


    public static void register() {

        openConfig = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "opens Config",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                CATEGORY));

        leave = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "executes /leave",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_L,
                CATEGORY));

    }

    public static void checkInputs(MinecraftClient client) {

        if (openConfig.wasPressed()) {
            client.setScreen(Config.createScreen(null));
        }

        if (leave.wasPressed()) {
            Objects.requireNonNull(client.getNetworkHandler()).sendChatCommand("leave");
        }
    }
}
