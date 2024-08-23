package mineward.utils.render;

import org.joml.Matrix4f;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.util.math.Vec3d;

public abstract class Shape {
    /**
     * Prepares a cube in the bufferBuilder from pos1 to pos2
     * 
     * @param buffer BufferBuilder
     * @param matrix Matrix4f
     * @param red    float, red value
     * @param green  float, green value
     * @param blue   float, blue value
     * @param alpha  float, alpha value
     * @param pos1   first position
     * @param pos2   second position
     */
    public static void buildCube(BufferBuilder buffer, Matrix4f matrix,
            Vec3d pos1, Vec3d pos2) {

        float x1 = (float) pos1.getX();
        float y1 = (float) pos1.getY();
        float z1 = (float) pos1.getZ();
        float x2 = (float) pos2.getX();
        float y2 = (float) pos2.getY();
        float z2 = (float) pos2.getZ();

        buffer.vertex(matrix, x1, y2, z1).next();
        buffer.vertex(matrix, x1, y2, z2).next();
        buffer.vertex(matrix, x2, y2, z2).next();
        buffer.vertex(matrix, x2, y2, z1).next();

        buffer.vertex(matrix, x1, y1, z2).next();
        buffer.vertex(matrix, x2, y1, z2).next();
        buffer.vertex(matrix, x2, y2, z2).next();
        buffer.vertex(matrix, x1, y2, z2).next();

        buffer.vertex(matrix, x2, y2, z2).next();
        buffer.vertex(matrix, x2, y1, z2).next();
        buffer.vertex(matrix, x2, y1, z1).next();
        buffer.vertex(matrix, x2, y2, z1).next();

        buffer.vertex(matrix, x2, y2, z1).next();
        buffer.vertex(matrix, x2, y1, z1).next();
        buffer.vertex(matrix, x1, y1, z1).next();
        buffer.vertex(matrix, x1, y2, z1).next();

        buffer.vertex(matrix, x1, y2, z1).next();
        buffer.vertex(matrix, x1, y1, z1).next();
        buffer.vertex(matrix, x1, y1, z2).next();
        buffer.vertex(matrix, x1, y2, z2).next();

        buffer.vertex(matrix, x1, y1, z1).next();
        buffer.vertex(matrix, x2, y1, z1).next();
        buffer.vertex(matrix, x2, y1, z2).next();
        buffer.vertex(matrix, x1, y1, z2).next();
    }

}
