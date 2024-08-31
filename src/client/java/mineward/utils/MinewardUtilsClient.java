package mineward.utils;

import mineward.utils.config.Config;
import mineward.utils.config.Keybinds;
import mineward.utils.render.ClientGUI;
import mineward.utils.render.ClientRender;
import mineward.utils.render.Cooldown;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.util.ActionResult;

public class MinewardUtilsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		Commands.register();
		Keybinds.register();
		ClientRender.register();
		ClientGUI.register();
		Cooldown.register();

		GwonkleHelper.resetWaypoints();


		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			PickupHighlight.updateParticles(client);
			Cooldown.runTickFunctions(client);
			Keybinds.checkInputs(client);
			Dimension.checkTime(client);
			//DamageTracking.update(client);
			TreasureHighlight.detect(client);
			//LottbsTimer.detect(client);
			//LottbsTimer.updateTime(client);
			//LottbsTimer.detectHealth(client);

			/*
			 * Commented out functions are either broken or 
			 * not possible at the moment for server reasons
			 */
		});
 
		UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			GwonkleHelper.resetWaypoints();
			PickupHighlight.checkPickup(entity, world);

			return ActionResult.PASS;
		});

		// load last
		Config.register();
	}
}