package mineward.utils;

import net.minecraft.client.MinecraftClient;

public abstract class Dimension {

    private static String dimension = "";
    private static double totalDeltaTime = 0;

    /**
     * Checks if sufficient amount of time has elapsed
     * @param client Minecraft client
     */
    public static void checkTime(MinecraftClient client) {
        totalDeltaTime += client.getTickDelta();
        if (totalDeltaTime < 2) {
            return;
        } else {
            totalDeltaTime = 0;
        }
        updateDimension();
    }

    /**
     * Updates the dimension String
     */
    public static void updateDimension() {
        MinecraftClient client = utils.getClient();
        if (client != null && client.player != null) {
            dimension = client.world.getRegistryKey().getValue().toString();
        }
    }

    // public static String getDimension(){
    //     return dimension;
    // }

    public static boolean inDimension(String str){
        return dimension.contains(str);
    }
}
