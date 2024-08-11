package mineward.utils.render;

import org.joml.Matrix4f;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.math.Vec3d;

public abstract class Shape {
    /**
     * Draws a Line
     * 
     * @param matrix4f Matrix4f
     * @param pos      Vec3d, first vertex
     * @param pos2     Vec3d, second vertex
     * @param r        float, red value
     * @param g        float, green value
     * @param b        float, blue value
     * @param a        float, alpha value
     */
    public static void drawLine3D(Matrix4f matrix4f, Vec3d pos, Vec3d pos2, float r, float g, float b, float a) {
        RenderSystem.setShaderColor(r, g, b, a);

        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION);

        bufferBuilder.vertex(matrix4f, (float) pos.x, (float) pos.y, (float) pos.z).next();
        bufferBuilder.vertex(matrix4f, (float) pos2.x, (float) pos2.y, (float) pos2.z).next();

        tessellator.draw();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    /**
     * Draws a triangle
     * 
     * @param matrix4f Matrix4f
     * @param pos      Vec3d, first vertex
     * @param pos2     Vec3d, second vertex
     * @param pos3     Vec3d, third vertex
     * @param r        float, red value
     * @param g        float, green value
     * @param b        float, blue value
     * @param a        float, alpha value
     */
    public static void fillTriangle(Matrix4f matrix4f, Vec3d pos, Vec3d pos2, Vec3d pos3, float r, float g, float b,
            float a) {
        RenderSystem.setShaderColor(r, g, b, a);

        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLES, VertexFormats.POSITION);

        bufferBuilder.vertex(matrix4f, (float) pos.x, (float) pos.y, (float) pos.z).next();
        bufferBuilder.vertex(matrix4f, (float) pos2.x, (float) pos2.y, (float) pos2.z).next();
        bufferBuilder.vertex(matrix4f, (float) pos3.x, (float) pos3.y, (float) pos3.z).next();

        tessellator.draw();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    /**
     * Draws a cube that is filled from pos to pos2
     * 
     * @param matrix4f Matrix4f
     * @param pos      Vec3d, first corner
     * @param pos2     Vec3d, second corner
     * @param r        float, red value
     * @param g        float, green value
     * @param b        float, blue value
     * @param a        float, alpha value
     */
    public static void fillCube(Matrix4f matrix4f, Vec3d pos, Vec3d pos2, float r, float g, float b, float a) {
        RenderSystem.setShaderColor(r, g, b, a);
        RenderSystem.disableDepthTest();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);

        buildCube(bufferBuilder, matrix4f, r, g, b, a, (float) pos.x, (float) pos.y, (float) pos.z, (float) pos2.x,
                (float) pos2.y, (float) pos2.z);

        tessellator.draw();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    /**
     * Draws a cube that is filled from pos to pos2
     * 
     * @param matrix4f Matrix4f
     * @param pos      Vec3d, first corner
     * @param pos2     Vec3d, second corner
     * @param r        float, red value
     * @param g        float, green value
     * @param b        float, blue value
     * @param a        float, alpha value
     */
    public static void drawCube(Matrix4f matrix4f, Vec3d pos, Vec3d pos2, float r, float g, float b, float a) {
        RenderSystem.setShaderColor(r, g, b, a);
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION);

        buildCube(bufferBuilder, matrix4f, r, g, b, a, (float) pos.x, (float) pos.y, (float) pos.z, (float) pos2.x,
                (float) pos2.y, (float) pos2.z);

        tessellator.draw();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    /**
     * Prepares a cube in the bufferBuilder from x1,y1,z1 to x2,y2,z2
     * 
     * @param buffer BufferBuilder
     * @param matrix Matrix4f
     * @param red    float, red value
     * @param green  float, green value
     * @param blue   float, blue value
     * @param alpha  float, alpha value
     * @param x1     float
     * @param y1     float
     * @param z1     float
     * @param x2     float
     * @param y2     float
     * @param z2     float
     */
    public static void buildCube(BufferBuilder buffer, Matrix4f matrix, float red, float green, float blue, float alpha,
            float x1, float y1, float z1, float x2, float y2, float z2) {
        buffer.vertex(matrix, x1, y2, z1).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x1, y2, z2).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x2, y2, z2).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x2, y2, z1).color(red, green, blue, alpha).next();

        buffer.vertex(matrix, x1, y1, z2).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x2, y1, z2).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x2, y2, z2).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x1, y2, z2).color(red, green, blue, alpha).next();

        buffer.vertex(matrix, x2, y2, z2).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x2, y1, z2).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x2, y1, z1).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x2, y2, z1).color(red, green, blue, alpha).next();

        buffer.vertex(matrix, x2, y2, z1).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x2, y1, z1).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x1, y1, z1).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x1, y2, z1).color(red, green, blue, alpha).next();

        buffer.vertex(matrix, x1, y2, z1).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x1, y1, z1).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x1, y1, z2).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x1, y2, z2).color(red, green, blue, alpha).next();

        buffer.vertex(matrix, x1, y1, z1).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x2, y1, z1).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x2, y1, z2).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x1, y1, z2).color(red, green, blue, alpha).next();
    }

}
