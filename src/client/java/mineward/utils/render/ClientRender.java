package mineward.utils.render;

import java.util.ArrayList;

import org.joml.Matrix4f;

import com.mojang.blaze3d.systems.RenderSystem;

import mineward.utils.Dimension;
import mineward.utils.GwonkleHelper;
import mineward.utils.PickupHighlight;
import mineward.utils.TreasureHighlight;
import mineward.utils.Waypoint;
import mineward.utils.config.Config;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public abstract class ClientRender {

    static float yOffsetWaypoint = 0;
    static int flyDirectionWaypoint = 1;
    static double rotationAngle = 0;
    static double totalTickDelta = 0;

    /**
     * Registers client rendering
     */
    public static void register() {
        WorldRenderEvents.BEFORE_DEBUG_RENDER.register(ClientRender::onWorldRender);

    }

    /**
     * On world render draws the client rendering
     * 
     * @param context
     */
    public static void onWorldRender(WorldRenderContext context) {
        if (Config.renderWaypoints && Dimension.inDimension("oceanarea")) {
            renderWaypoints(context);
        }
        if (Config.renderPickups && Dimension.inDimension("anvahar")) {
            renderPickups(context.matrixStack(), context.camera());
        }

        if (Config.renderTreasures) {
            renderTreasures(context.matrixStack(), context.camera());
        }
    }

    /**
     * Renders the waypoints remaining
     * 
     * @param context
     */
    public static void renderWaypoints(WorldRenderContext context) {

        // if you need more performance
        if (Config.usePerformanceMode) {
            renderPerformanceWaypoints(context.matrixStack(), context.camera());
        } else {

            // floating up and down
            yOffsetWaypoint += 0.03 * context.tickDelta() * flyDirectionWaypoint * lerp(yOffsetWaypoint);
            if (yOffsetWaypoint < 0) {
                flyDirectionWaypoint = 1;
            } else if (yOffsetWaypoint > 0.5) {
                flyDirectionWaypoint = -1;
            }

            // rotation
            rotationAngle += (2 * context.tickDelta()) % 360;

            totalTickDelta += context.tickDelta();

            renderCross(context.matrixStack(), context.camera());
        }
    }

    /**
     * Draws a cube at all the waypoint positions insted of a cross
     * 
     * @param matrixStack MatrixStack
     * @param camera      Camera
     */
    public static void renderPerformanceWaypoints(MatrixStack matrixStack, Camera camera) {
        RenderSystem.setShaderColor(1, 0, 0, 1);
        ArrayList<Waypoint> waypoints = GwonkleHelper.getWaypoints();

        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        RenderSystem.setShader(GameRenderer::getPositionProgram);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
        for (int i = 0; i < waypoints.size(); i++) {

            Vec3d pos = waypoints.get(i).getPos();
            Vec3d cameraPos = camera.getPos();

            matrixStack.push();
            matrixStack.translate(pos.x - cameraPos.x, pos.y - cameraPos.y, pos.z - cameraPos.z);
            Matrix4f positionMatrix = matrixStack.peek().getPositionMatrix();

            Shape.buildCube(bufferBuilder, positionMatrix, new Vec3d(0, 0, 0), new Vec3d(1, 1, 1));

            matrixStack.pop();
        }

        tessellator.draw();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    /**
     * rennders a cross at all waypoints position that should be rendered
     * 
     * @param matrixStack MatrixStack
     * @param camera      Camera
     */
    private static void renderCross(MatrixStack matrixStack, Camera camera) {
        RenderSystem.setShaderColor(1, 0, 0, 1);
        ArrayList<Waypoint> waypoints = GwonkleHelper.getWaypoints();

        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        RenderSystem.setShader(GameRenderer::getPositionProgram);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
        for (int i = 0; i < waypoints.size(); i++) {
            Vec3d pos = waypoints.get(i).getPos();
            Vec3d cameraPos = camera.getPos();

            matrixStack.push();
            matrixStack.translate(pos.x - cameraPos.x, pos.y - cameraPos.y + 1 + yOffsetWaypoint, pos.z - cameraPos.z);
            Matrix4f positionMatrix = matrixStack.peek().getPositionMatrix();

            positionMatrix.rotate((float) Math.toRadians(rotationAngle), 0, 1, 0);

            positionMatrix.rotate((float) Math.toRadians(-45), 0, 0, 1);
            Shape.buildCube(bufferBuilder, positionMatrix, new Vec3d(1.5, 1, 0), new Vec3d(-2.5, 0, 1));

            positionMatrix.rotate((float) Math.toRadians(90), 0, 0, 1);
            Shape.buildCube(bufferBuilder, positionMatrix, new Vec3d(2.5, 1, 0), new Vec3d(-1.5, 0, 1));

            matrixStack.pop();
        }

        tessellator.draw();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    /**
     * Renders all pickups
     * 
     * @param matrixStack MatrixStack
     * @param camera      Camera
     */
    public static void renderPickups(MatrixStack matrixStack, Camera camera) {
        RenderSystem.setShaderColor(Config.pickupRed, Config.pickupGreen, Config.pickupBlue, 1);
        ArrayList<Particle> particles = PickupHighlight.getParticles();

        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        RenderSystem.setShader(GameRenderer::getPositionProgram);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
        for (int i = 0; i < particles.size(); i++) {

            // get positions
            Box box = particles.get(i).getBoundingBox();
            Vec3d pos = new Vec3d(box.minX, box.minY, box.minZ);
            Vec3d size = new Vec3d(box.getLengthX(), box.getLengthY(), box.getLengthZ());
            Vec3d cameraPos = camera.getPos();

            matrixStack.push();
            matrixStack.translate(pos.x - cameraPos.x, pos.y - cameraPos.y, pos.z - cameraPos.z);
            Matrix4f positionMatrix = matrixStack.peek().getPositionMatrix();

            Shape.buildCube(bufferBuilder, positionMatrix, new Vec3d(0, 0, 0), size);

            matrixStack.pop();
        }

        tessellator.draw();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    /**
     * Smoothens the waypoint triangles movement
     * 
     * @param x the value to smooth
     * @return the value smoothened
     */
    public static double lerp(double x) {
        return Math.sin(2 * x * Math.PI) + 0.1;
    }

    /**
     * Renders all treasure chests
     * 
     * @param matrixStack
     * @param camera
     */
    public static void renderTreasures(MatrixStack matrixStack, Camera camera) {
        RenderSystem.setShaderColor(Config.treasureRed, Config.treasureGreen, Config.treasureBlue, 1);
        ArrayList<LivingEntity> entities = TreasureHighlight.getEntities();

        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        RenderSystem.setShader(GameRenderer::getPositionProgram);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
        for (int i = 0; i < entities.size(); i++) {

            // get positions
            Box box = entities.get(i).getBoundingBox();
            Vec3d pos = new Vec3d(box.minX, box.minY, box.minZ);
            Vec3d size = new Vec3d(box.getLengthX(), 0.5, box.getLengthZ());
            Vec3d cameraPos = camera.getPos();

            matrixStack.push();
            matrixStack.translate(pos.x - cameraPos.x, pos.y - cameraPos.y + 1.5, pos.z - cameraPos.z);
            Matrix4f positionMatrix = matrixStack.peek().getPositionMatrix();

            Shape.buildCube(bufferBuilder, positionMatrix, new Vec3d(0, 0, 0), size);

            matrixStack.pop();
        }

        tessellator.draw();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }
}
