package mineward.utils.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mineward.utils.Config;
import mineward.utils.PickupHighlight;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;

@Mixin(ParticleManager.class)

public class ParticalMixin {
    @Inject(method = "addParticle", at = @At("RETURN"))
    private void onAddParticle(ParticleEffect parameters, double x, double y, double z, double velocityX,
            double velocityY, double velocityZ, CallbackInfoReturnable<Particle> cir) {
        if (!Config.renderPickups) {
            return;
        }
        if (parameters.getType() == ParticleTypes.WAX_ON) {
            Particle particle = cir.getReturnValue();
            if (particle != null) {
                PickupHighlight.add(particle);
            }
        }

    }
}
