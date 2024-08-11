package mineward.utils.render;

import java.util.ArrayList;

import org.joml.Matrix4f;

import mineward.utils.GwonkleHelper;
import mineward.utils.PickupHighlight;
import mineward.utils.Waypoint;
import mineward.utils.utils;
import mineward.utils.config.Config;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
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
        if (Config.renderWaypoints) {
            renderWaypoints(context);
        }
        if (Config.renderPickups){
            renderPickups(context);

        }
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
     * Renders the waypoints remaining
     * 
     * @param context
     */
    public static void renderWaypoints(WorldRenderContext context) {

        // floating up and down
        rotationAngle += (2 * context.tickDelta()) % 360;
        yOffsetWaypoint += 0.03 * context.tickDelta() * flyDirectionWaypoint * lerp(yOffsetWaypoint);
        if (yOffsetWaypoint < 0) {
            flyDirectionWaypoint = 1;
        } else if (yOffsetWaypoint > 0.5) {
            flyDirectionWaypoint = -1;
        }

        // rotation
        totalTickDelta += context.tickDelta();

        ArrayList<Waypoint> waypoints = GwonkleHelper.getWaypoints();

        for (int i = 0; i < waypoints.size(); i++) {
            Vec3d pos = waypoints.get(i).getPos();
            renderCross(context.matrixStack(), context.camera(), pos.add(0.5, 3 + yOffsetWaypoint, 0.5));
        }

    }

    /**
     * Renders a cross at the selected position
     * 
     * @param matrixStack MatrixStack
     * @param camera      Camera
     * @param pos         Vec3d
     */
    private static void renderCross(MatrixStack matrixStack, Camera camera, Vec3d pos) {
        Vec3d cameraPos = camera.getPos();
        MinecraftClient client = utils.getClient();
        PlayerEntity player = client.player;

        double distance = utils.getDistance(pos, player.getPos());

        matrixStack.push();
        matrixStack.translate(pos.x - cameraPos.x, pos.y - cameraPos.y, pos.z - cameraPos.z);

        Matrix4f positionMatrix = matrixStack.peek().getPositionMatrix();
        positionMatrix.rotate((float) Math.toRadians(rotationAngle), 0, 1, 0);

        positionMatrix.rotate((float) Math.toRadians(-45), 0, 0, 1);
        Shape.fillCube(positionMatrix, new Vec3d(1.5, 1, 0), new Vec3d(-2.5, 0, 1), 1f, 0f, 0f, 1f);
        if (distance > 50) {
            Shape.drawCube(positionMatrix, new Vec3d(1.5, 1, 0), new Vec3d(-2.5, 0, 1), 0f, 0f, 0f, 1f);
        }
        positionMatrix.rotate((float) Math.toRadians(90), 0, 0, 1);
        Shape.fillCube(positionMatrix, new Vec3d(2.5, 1, 0), new Vec3d(-1.5, 0, 1), 1f, 0f, 0f, 1f);
        if (distance > 50) {
            Shape.drawCube(positionMatrix, new Vec3d(2.5, 1, 0), new Vec3d(-1.5, 0, 1), 0f, 0f, 0f, 1f);
        }
        matrixStack.pop();
    }

    public static void renderPickups(WorldRenderContext context) {
        ArrayList<Particle> particles = PickupHighlight.getParticles();
        for (int i = 0; i < particles.size(); i++) {
            renderParticle(context.matrixStack(), context.camera(), particles.get(i).getBoundingBox());
        }

    }

    public static void renderParticle(MatrixStack matrixStack, Camera camera, Box box) {
        Vec3d cameraPos = camera.getPos();
        Vec3d pos = new Vec3d(box.minX, box.minY, box.minZ);
        Vec3d size = new Vec3d(box.getXLength(), box.getYLength(), box.getZLength());
        matrixStack.push();
        matrixStack.translate(pos.x - cameraPos.x, pos.y - cameraPos.y, pos.z - cameraPos.z);
        Matrix4f positionMatrix = matrixStack.peek().getPositionMatrix();

        Shape.fillCube(positionMatrix, new Vec3d(0,0,0), size, PickupHighlight.red, PickupHighlight.green, PickupHighlight.blue, 1f);

        matrixStack.pop();
    }
}
