package mineward.utils.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mineward.utils.utils;
import mineward.utils.config.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

@Mixin(HeadFeatureRenderer.class)

public abstract class HeadMixin <T extends LivingEntity> {

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo info) {
        MinecraftClient client = utils.getClient();
        if (Config.renderHead) {
            return;
        } else if(livingEntity.getName().contains(client.player.getName())){
            info.cancel();
            return;
        }
    }
}
