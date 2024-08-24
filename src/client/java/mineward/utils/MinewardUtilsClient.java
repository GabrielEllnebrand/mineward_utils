package mineward.utils;

import mineward.utils.config.Config;
import mineward.utils.config.Keybinds;
import mineward.utils.render.ClientGUI;
import mineward.utils.render.ClientRender;
import mineward.utils.render.Cooldown;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.LivingEntity;
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
			DamageTracking.update(client);
		});

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			System.out.println(entity);
			if (entity != null) {
				if(entity instanceof LivingEntity){
					DamageTracking.livingEntity = (LivingEntity) entity;
				}
			}
			return ActionResult.PASS;
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