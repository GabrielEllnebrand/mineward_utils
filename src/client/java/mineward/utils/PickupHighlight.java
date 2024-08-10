package mineward.utils;

import java.util.ArrayList;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;

public class PickupHighlight {

    private static ArrayList<Particle> particles = new ArrayList<>();
    public static float red = 0;
    public static float blue = 0;
    public static float green = 0;

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

    public static String color(){
        return red + " "  + blue + " " + green;
    }
}
