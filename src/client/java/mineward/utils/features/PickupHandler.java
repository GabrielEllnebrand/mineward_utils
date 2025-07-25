package mineward.utils.features;

import config.practical.manager.ConfigValue;
import mineward.utils.utils.config.Config;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

import java.util.ArrayList;

public class PickupHandler {

    private static final int REFRESH_TICK = 5;
    private static final ArrayList<Particle> pickups = new ArrayList<>();

    @ConfigValue
    public static int pickupCount;

    @ConfigValue
    public static boolean highLightPickups = false;

    @ConfigValue
    public static int pickupColor = 0xff00ffff;

    private static int tick = 0;
    public static void update() {

        tick++;
        if (tick < REFRESH_TICK) return;
        tick = 0;

        for(int i = pickups.size()-1; i >= 0; i--){
            if (!pickups.get(i).isAlive()) {
                pickups.remove(i);
            }
        }
    }

    public static void checkPickup(Entity entity) {
        if (entity.getType() == EntityType.ARMOR_STAND) {
            pickupCount++;
            Config.manager.save();
        }
    }

    public static void addPickup(Particle particle) {
        pickups.add(particle);
    }

    public static void clearPickups() {
        pickups.clear();
    }

    public static ArrayList<Particle> getPickups() {
        return new ArrayList<>(pickups);
    }

    public static int getCount() {return pickupCount;}
}
