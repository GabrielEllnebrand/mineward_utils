package mineward.utils.mixin.client;

import mineward.utils.features.PickupHandler;
import mineward.utils.utils.location.Location;
import mineward.utils.utils.location.Locations;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParticleManager.class)
public class PickupAddMixin {


    @Inject(method = "addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)Lnet/minecraft/client/particle/Particle;", at = @At("RETURN"))
    private void onAddParticle(ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, CallbackInfoReturnable<Particle> cir) {

        if (!Location.inLocation(Locations.ANVAHAR_TOMB)) return;
        if (parameters.getType() != ParticleTypes.WAX_ON) return;
        if (!PickupHandler.highLightPickups) return;

        Particle particle = cir.getReturnValue();
        if (particle == null) return;

        PickupHandler.addPickup(particle);
    }
}
