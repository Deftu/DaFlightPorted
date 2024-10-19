package dev.deftu.daflight.mixins;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.deftu.daflight.handlers.MovementHandler;

@Mixin({GameRenderer.class})
public class GameRendererMixin {
    @Inject(method = "bobView", at = @At("HEAD"), cancellable = true)
    private void dfp$cancelViewBobbing(MatrixStack stack, float tickDelta, CallbackInfo ci) {
        if (MovementHandler.shouldDisableViewBobbing()) {
            ci.cancel();
        }
    }
}
