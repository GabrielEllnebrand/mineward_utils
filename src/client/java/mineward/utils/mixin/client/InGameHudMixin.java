package mineward.utils.mixin.client;

import mineward.utils.features.HUDHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "renderFood", at = @At("HEAD"), cancellable = true)
    public void handleFoodRender(DrawContext context, PlayerEntity player, int top, int right, CallbackInfo ci) {
        if (HUDHandler.hideHunger) ci.cancel();
    }

    @Inject(method = "renderArmor", at = @At("HEAD"), cancellable = true)
    private static void handleArmourRender(DrawContext context, PlayerEntity player, int i, int j, int k, int x, CallbackInfo ci) {
        if (HUDHandler.hideArmourBar) ci.cancel();
    }

    @Inject(method = "renderHealthBar", at = @At("HEAD"), cancellable = true)
    public void handleHealthRender(DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci) {
        if (HUDHandler.hideHearts) ci.cancel();
    }

    @Inject(method = "renderOverlayMessage", at = @At("HEAD"), cancellable = true)
    public void handleActionBarRender(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if(HUDHandler.hideActionBar) ci.cancel();
    }

    @Unique
    private static final Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?/\\d+(\\.\\d+)?");

    @Inject(method = "setOverlayMessage", at = @At("HEAD"))
    public void msg(Text message, boolean tinted, CallbackInfo ci) {
        Matcher matcher = pattern.matcher(message.getString());

        int argsFound = 0;
        double[] nums = new double[6];

        try {
            while (matcher.find() && argsFound < 3) {
                String str = matcher.group();
                String[] strs = str.split("/");
                nums[argsFound * 2] = Double.parseDouble(strs[0]);
                nums[argsFound * 2 + 1] = Double.parseDouble(strs[1]);
                argsFound++;
            }
        } catch (NumberFormatException e) {
            argsFound = 0;
        }

        //if incorrect msg doesn't override with 0s
        if (argsFound == 0) return;

        HUDHandler.currentHealth = nums[0];
        HUDHandler.maxHealth = nums[1];

        HUDHandler.currentMana = nums[2];
        HUDHandler.maxMana = nums[3];

        HUDHandler.currentBlaze = nums[4];
        HUDHandler.maxBlaze = nums[5];
    }
}
