package mineward.utils.mixin.client;

import net.minecraft.client.network.message.MessageHandler;
import net.minecraft.text.Text;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mineward.utils.GwonkleHelper;
import mineward.utils.OracleSolver;

@Mixin(MessageHandler.class)
public class MessageMixin {
	@Inject(method = "onGameMessage", at = @At("HEAD"))
	private void handle(Text message, boolean overlay, CallbackInfo info) {
		String string = message.getString();
		if (string.contains("equal to") && string.contains("Is")) {
			OracleSolver.parseString(string);
		}

		if(string.contains("treasure") && string.contains("blocks away")){
			GwonkleHelper.checkWaypoints(string);
		}
	}

}