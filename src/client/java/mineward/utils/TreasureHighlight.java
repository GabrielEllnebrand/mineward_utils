package mineward.utils;

import java.util.ArrayList;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public abstract class TreasureHighlight {
    private static ArrayList<LivingEntity> entities = new ArrayList<>();
    private static float totalTickDelta = 0;

    public static void detect(MinecraftClient client) {

        totalTickDelta += client.getTickDelta();

        if (totalTickDelta < 0.5) {
            return;
        }

        totalTickDelta = 0;

        if (Dimension.inDimension("ancient_tomb")) {
            updateEntities(client);
        } else {
            entities.clear();
        }
    }

    /**
     * sets entities to all entities in the world
     * 
     * @param client
     */
    public static void updateEntities(MinecraftClient client) {
        entities.clear();

        Iterable<Entity> ireatable = client.world.getEntities();
        for (Entity entity : ireatable) {
            if (entity == null) {
                return;
            }

            if (entity instanceof LivingEntity && entity.getType() == EntityType.ARMOR_STAND) {
                Iterable<ItemStack> armour = entity.getArmorItems();
                for (ItemStack item : armour) {
                    if (item.toString().contains("player_head")) {
                        entities.add((LivingEntity) entity);
                    }
                }
            }
        }
    }

    public static ArrayList<LivingEntity> getEntities() {
        return entities;
    }
}
