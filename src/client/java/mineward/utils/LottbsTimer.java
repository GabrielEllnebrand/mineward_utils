package mineward.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class LottbsTimer {

    private static LivingEntity lottbs;
    private static float time = 0;
    private static float totalTickDelta = 0;
    private static float hp;

    public static void detect(MinecraftClient client) {

        totalTickDelta += client.getTickDelta();

        if (totalTickDelta < 0.5) {
            return;
        } else {
            totalTickDelta = 0;
        }
 
        if(!Dimension.inDimension("hyrrill")){
            lottbs = null;
            return;
        }

        if (time > 0) {
            return;
        }

        if (lottbs != null) {
            return;
        }

        Iterable<Entity> ireatable = client.world.getEntities();
        for (Entity entity : ireatable) {
            if (entity == null) {
                return;
            }

            if (entity instanceof LivingEntity && entity.getType() == EntityType.WITHER_SKELETON) {
                Iterable<ItemStack> armour = entity.getArmorItems();
                for (ItemStack item : armour) {
                    if (item.toString().contains("player_head")) {
                        lottbs = (LivingEntity)entity;
                    }
                }
            }
        }
    }
    
    public static void detectHealth(MinecraftClient client){
        if (lottbs == null) {
            return;
        }

        float currhp = lottbs.getHealth();
        if (currhp < hp) {
            time = 10;
            hp = currhp;
        }
    }

    public static void updateTime(MinecraftClient client){
        if (time < 0) {
            time -= client.getTickDelta();
        } else {
            time = 0;
        }
    }

    public static float getTime(){
        return time;
    }

    public static LivingEntity getLottbs(){
        return lottbs;
    }

}
