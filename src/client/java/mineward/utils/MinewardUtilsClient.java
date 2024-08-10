package mineward.utils;
import mineward.utils.render.ClientGUI;
import mineward.utils.render.ClientRender;
import mineward.utils.render.Cooldown;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class MinewardUtilsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		GwonkleHelper.register();
		Commands.register();
		Keybinds.register();
		ClientRender.register();
		ClientGUI.register();
		Cooldown.register();

		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			PickupHighlight.updateParticles(client);
			Cooldown.runTickFunctions(client);
			Keybinds.checkInputs(client);
		});

		//load last
		Config.register();
	}
}