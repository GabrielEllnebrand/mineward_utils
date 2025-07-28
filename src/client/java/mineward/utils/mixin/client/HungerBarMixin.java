package mineward.utils.mixin.client;

import mineward.utils.utils.config.MiscValues;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class HungerBarMixin {

    @Inject(method="renderFood", at=@At("HEAD"), cancellable = true)
    public void handleFoodRender(DrawContext context, PlayerEntity player, int top, int right, CallbackInfo ci) {
        if(MiscValues.hideHunger) {
            ci.cancel();
        }
    }
}
