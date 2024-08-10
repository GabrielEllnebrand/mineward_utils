package mineward.utils.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mineward.utils.Config;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;

@Mixin(HeadFeatureRenderer.class)

public class HeadMixin {

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    public void render(CallbackInfo info) {
        if (Config.renderHead) {
            return;
        } else {
            info.cancel();
            return;
        }
    }
}
