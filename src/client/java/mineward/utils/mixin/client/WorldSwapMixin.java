package mineward.utils.mixin.client;

import mineward.utils.utils.location.Location;
import mineward.utils.utils.location.Locations;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class WorldSwapMixin {
    @Inject(at = @At("HEAD"), method = "setWorld")
    private void onWorldSwap(@Nullable ClientWorld world, CallbackInfo info) {
        if (world == null) return;

        String dimension = world.getRegistryKey().getValue().toString();

        dimension = dimension.replace("minecraft:", "").toUpperCase();

        //incase multiple instances
        if (Character.isDigit(dimension.charAt(dimension.length() - 1))) {
            int index = dimension.lastIndexOf("_");
            dimension = dimension.substring(0, index);
        }
        Locations location;

        try {
            location = Locations.valueOf(dimension);
        } catch (IllegalArgumentException ignored) {
            location = Locations.NONE;
        }

        Location.setLocation(location);
    }

}
