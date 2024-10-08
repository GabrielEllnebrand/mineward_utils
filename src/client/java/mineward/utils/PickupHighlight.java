package mineward.utils;

import java.util.ArrayList;

import mineward.utils.config.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public abstract class PickupHighlight {

    private static String instanceName = "anvahar";
    private static ArrayList<Particle> particles = new ArrayList<>();

    /**
     * Updates the particle list and checks if there is any particles that needs to be removed
     * @param client Minecraft client, incase it is null
     */
    public static void updateParticles(MinecraftClient client){
        if(client == null){
            return;
        }
        for(int i = particles.size()-1; i >= 0; i--){
            if (!particles.get(i).isAlive()) {
                particles.remove(i);
            }
        }

        if (!Dimension.inDimension(instanceName)) {
            particles.clear();
        }
    }

    public static ArrayList<Particle> getParticles(){
        return particles;
    }

    public static void clear(){
        particles.clear();
    }

    public static void add(Particle particle){
        particles.add(particle);
    }

    public static void increasePickupCount(){
        Config.pickupCount++;
        Config.set("pickupCount", Config.pickupCount);
    }

    public static void checkPickup(Entity entity, World world){
        if (world.getRegistryKey().getValue().toString().contains(instanceName) && entity.getType() == EntityType.ARMOR_STAND) {
            increasePickupCount();
        }
    }
}
