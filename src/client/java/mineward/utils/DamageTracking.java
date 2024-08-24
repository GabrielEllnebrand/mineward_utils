package mineward.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

public abstract class DamageTracking {
    public static LivingEntity livingEntity;

    private static float lastHealth;
    private static float totalDamage;
    private static float lastHit;
    private static float totalTickDelta = 0;
    private static float timeSinceLastDamage = 0;
    private static float damageDuration = 0;
    private static float dps = 0;

    public static void update(MinecraftClient client) {

        totalTickDelta += client.getTickDelta();

        if (livingEntity == null && totalTickDelta > 0.25) {
            findDummy(client);
        }

        // incase its still null
        if (livingEntity == null) {
            return;
        }

        if (livingEntity.isDead()) {
            livingEntity = null;
        } else {
            float hp = livingEntity.getHealth();
            if (hp < lastHealth) {
                lastHit = lastHealth - hp;
                totalDamage += lastHit;
                timeSinceLastDamage = 0;
            }

            lastHealth = hp;
        }

        timeSinceLastDamage += client.getTickDelta();

        if (timeSinceLastDamage > 5) {
            damageDuration = 0;
            totalDamage = 0;
        } else {
            damageDuration += client.getTickDelta();
        }

        if (damageDuration != 0 && totalTickDelta > 0.25) {
            dps = totalDamage / damageDuration;
        }

        if (totalTickDelta > 0.25) {
            totalTickDelta = 0;
        }
    }

    private static void findDummy(MinecraftClient client) {
        if (!Dimension.getDimension().contains("overworld")) {
            return;
        }

        if (client.player == null) {
            return;
        }

        if (utils.inRange(client.player.getPos(), new Vec3d(1128, 65, 1006), 200)) {
            Iterable<Entity> iterator = client.world.getEntities();
            for (Entity entity : iterator) {
                if (entity instanceof LivingEntity) {
                    LivingEntity possibleEntity = (LivingEntity) entity;
                    if (possibleEntity.getMaxHealth() == 1024) {
                        livingEntity = possibleEntity;
                    }
                }
            }
        }
    }

    public static LivingEntity getEntity() {
        return livingEntity;
    }

    public static float getDps() {
        return dps;
    }

    public static void resetTarget() {
        livingEntity = null;
    }
}
