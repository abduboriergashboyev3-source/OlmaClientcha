package uz.ozbek.client.mixin;

import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uz.ozbek.client.OzbekClient;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
    @Inject(method = "sendMessage", at = @At("HEAD"), cancellable = true)
    private void ozbek$dotCommand(String chatText, boolean addToHistory, CallbackInfo ci) {
        if (chatText != null && chatText.startsWith(".")) {
            if (chatText.startsWith(".friend") || chatText.startsWith(".cfg")) {
                OzbekClient.command(chatText);
                ci.cancel();
            }
        }
    }
}
