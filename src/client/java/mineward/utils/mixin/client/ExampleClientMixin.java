package mineward.utils.mixin.client;

import net.minecraft.client.network.message.MessageHandler;
import net.minecraft.text.Text;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mineward.utils.OracleSolver;


@Mixin(MessageHandler.class)
public class ExampleClientMixin {
	@Inject(method = "onGameMessage", at = @At("HEAD"))
	private void handle(Text message, boolean overlay, CallbackInfo info){
		OracleSolver.parseString(message.getString());
	}

}