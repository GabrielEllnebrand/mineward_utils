package mineward.utils.utils.rendering;

import mineward.utils.features.EntityHandler;
import mineward.utils.features.OracleSolver;
import mineward.utils.features.PickupHandler;
import mineward.utils.utils.location.Location;
import mineward.utils.utils.location.Locations;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexRendering;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public class WorldRender {

    private static final float HALF_HEAD_SIZE = 0.3f;
    private static final float HEAD_Y_OFFSET = 1.35f;

    public static void render(@NotNull WorldRenderContext context) {

        Camera camera = context.camera();
        Vec3d cameraPos = camera.getPos();

        MatrixStack matrixStack = context.matrixStack();
        if (matrixStack == null) return;
        matrixStack.push();
        matrixStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

        VertexConsumerProvider consumers = context.consumers();
        if (consumers == null) return;
        VertexConsumer buffer = consumers.getBuffer(RenderLayers.getRenderLayer(false));

        if (Location.inLocation(Locations.ANVAHAR_TOMB)) {
            renderPickups(matrixStack, buffer);
            renderOracleAnswer(matrixStack, buffer);
        }

        if (Location.inAncient()) {
            renderChests(matrixStack, buffer);
        }

        renderCrystals(matrixStack, buffer);

        matrixStack.pop();
    }

    private static float[] toFloats(int argb) {
        float r = ((argb >> 16) & 0xFF) / 255f;
        float g = ((argb >> 8) & 0xFF) / 255f;
        float b = (argb & 0xFF) / 255f;
        float a = ((argb >> 24) & 0xFF) / 255f;
        return new float[]{r, g, b, a};
    }

    private static void renderPickups(MatrixStack matrixStack, VertexConsumer buffer) {
        if (!PickupHandler.highLightPickups) return;

        float[] color = toFloats(PickupHandler.pickupColor);

        for (Particle particle : PickupHandler.getPickups()) {
            Box box = particle.getBoundingBox();

            VertexRendering.drawFilledBox(matrixStack, buffer, box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, color[0], color[1], color[2], color[3]);
        }
    }

    private static void renderOracleAnswer(MatrixStack matrixStack, VertexConsumer buffer) {
        if (!OracleSolver.solveOracle || !OracleSolver.activeQuestion) return;
        Box box = OracleSolver.currentBox;
        float[] color = toFloats(OracleSolver.answerColor);
        VertexRendering.drawFilledBox(matrixStack, buffer, box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, color[0], color[1], color[2], color[3]);
    }

    private static void renderChests(MatrixStack matrixStack, VertexConsumer buffer) {
        if (!EntityHandler.highlightChests) return;
        float[] color = toFloats(EntityHandler.chestColor);
        for (Vec3d pos: EntityHandler.getChests()) {
            VertexRendering.drawFilledBox(matrixStack, buffer, pos.x - HALF_HEAD_SIZE, pos.y + HEAD_Y_OFFSET, pos.z - HALF_HEAD_SIZE, pos.x + HALF_HEAD_SIZE, pos.y + HEAD_Y_OFFSET + 2 * HALF_HEAD_SIZE, pos.z +HALF_HEAD_SIZE, color[0], color[1], color[2], color[3]);
        }
    }

    private static void renderCrystals(MatrixStack matrixStack, VertexConsumer buffer) {
        if (!EntityHandler.highlightCrystals) return;
        float[] color = toFloats(EntityHandler.crystalColor);
        for (Vec3d pos: EntityHandler.getCrystals()) {
            VertexRendering.drawFilledBox(matrixStack, buffer, pos.x - HALF_HEAD_SIZE, pos.y + HEAD_Y_OFFSET, pos.z - HALF_HEAD_SIZE, pos.x + HALF_HEAD_SIZE, pos.y + HEAD_Y_OFFSET + 2 * HALF_HEAD_SIZE, pos.z +HALF_HEAD_SIZE, color[0], color[1], color[2], color[3]);
        }
    }

}
