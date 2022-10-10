package xyz.deftu.daflight.mixins;

import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.deftu.daflight.events.KeyInputEvent;

@Mixin({Keyboard.class})
public class KeyboardMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "onKey", at = @At("HEAD"))
    private void dfp$onKey(long handle, int key, int scancode, int action, int mod, CallbackInfo ci) {
        if (handle != client.getWindow().getHandle())
            return;

        KeyInputEvent.EVENT.invoker().onKey(key, scancode, action, mod);
    }
}
