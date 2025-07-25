package mineward.utils.utils.rendering;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat;
import mineward.utils.utils.Constants;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

public class RenderLayers {

    private static final RenderPipeline THROUGH_WALL_FILLED_PIPELINE = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.POSITION_COLOR_SNIPPET)
            .withLocation(Identifier.of(Constants.NAMESPACE, "through_walls_filled"))
            .withVertexFormat(VertexFormats.POSITION_COLOR, VertexFormat.DrawMode.TRIANGLE_STRIP)
            .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
            .build());

    private static final RenderPipeline FILLED_PIPELINE = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.POSITION_COLOR_SNIPPET)
            .withLocation(Identifier.of(Constants.NAMESPACE, "filled"))
            .withVertexFormat(VertexFormats.POSITION_COLOR, VertexFormat.DrawMode.TRIANGLE_STRIP)
            .build());

    private static final RenderLayer.MultiPhase THROUGH_WALL_FILLED_LAYER =
            RenderLayer.of("through_wall_filled", RenderLayer.DEFAULT_BUFFER_SIZE, false, true, THROUGH_WALL_FILLED_PIPELINE, RenderLayer.MultiPhaseParameters.builder()
                    .layering(RenderPhase.VIEW_OFFSET_Z_LAYERING)
                    .build(false));

    private static final RenderLayer.MultiPhase FILLED_LAYER =
            RenderLayer.of("filled", RenderLayer.DEFAULT_BUFFER_SIZE, false, true, FILLED_PIPELINE, RenderLayer.MultiPhaseParameters.builder()
                    .layering(RenderPhase.VIEW_OFFSET_Z_LAYERING)
                    .build(false));

    public static RenderLayer getRenderLayer(boolean doDepthCheck) {
        return doDepthCheck ? FILLED_LAYER : THROUGH_WALL_FILLED_LAYER;
    }
}
