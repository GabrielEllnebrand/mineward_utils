package mineward.utils;

import mineward.utils.features.EntityHandler;
import mineward.utils.features.PickupHandler;
import mineward.utils.utils.Keybinds;
import mineward.utils.utils.config.Config;
import mineward.utils.utils.location.Location;
import mineward.utils.utils.rendering.GUIRender;
import mineward.utils.utils.rendering.WorldRender;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.util.ActionResult;

public class MinewardUtilsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		Config.manager.load();
		Keybinds.register();

		WorldRenderEvents.BEFORE_DEBUG_RENDER.register(WorldRender::render);
		HudLayerRegistrationCallback.EVENT.register(GUIRender::render);
		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			PickupHandler.update();
			Keybinds.checkInputs(client);
			EntityHandler.update(client);
		});
		UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			switch (Location.getLocation()) {
				case ANVAHAR_TOMB -> PickupHandler.checkPickup(entity);
			}


			return ActionResult.PASS;
		});
	}

}