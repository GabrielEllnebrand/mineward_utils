package mineward.utils;

import net.minecraft.util.math.Vec3d;

public class Waypoint {
    private Vec3d pos;
    private String name;

    public Waypoint(String name, Vec3d pos) {
        this.name = name;
        this.pos = pos;
    }

    public Vec3d getPos() {
        return pos;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " " + pos;
    }

}
