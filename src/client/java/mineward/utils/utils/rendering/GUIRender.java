package mineward.utils.utils.rendering;

import mineward.utils.features.PickupHandler;
import mineward.utils.utils.Constants;
import mineward.utils.utils.location.Location;
import mineward.utils.utils.location.Locations;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.fabricmc.fabric.api.client.rendering.v1.LayeredDrawerWrapper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class GUIRender {

    private static final String PATH = "render_gui_layer";

    public static void render(LayeredDrawerWrapper layeredDrawer) {
        layeredDrawer.attachLayerAfter(IdentifiedLayer.MISC_OVERLAYS, Identifier.of(Constants.NAMESPACE, PATH),
                (context, tickCounter) -> {

                    if (Location.inLocation(Locations.ANVAHAR_TOMB)) {
                        //TODO: remove magic numbers and make it flexible
                        context.drawText(MinecraftClient.getInstance().textRenderer, "Pickups: " + PickupHandler.getCount(), 70, 240, 0xffffffff, true);
                    }

        });
    }
}
