package mineward.utils.mixin.client;

import mineward.utils.features.OracleSolver;
import net.minecraft.client.network.message.MessageHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MessageHandler.class)
public class MessageMixin {
    @Inject(method = "onGameMessage", at=@At("HEAD"))
    private void parseMessage(Text message, boolean overlay, CallbackInfo ci) {
            OracleSolver.parse(message);



    }

}
