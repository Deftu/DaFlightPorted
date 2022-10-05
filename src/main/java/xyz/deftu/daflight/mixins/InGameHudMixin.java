package xyz.deftu.daflight.mixins;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.deftu.daflight.handlers.HudHandler;

@Mixin({InGameHud.class})
public class InGameHudMixin {
    @Inject(method = "render", at = @At("RETURN"))
    private void dfp$onHudRendered(MatrixStack stack, float tickDelta, CallbackInfo ci) {
        HudHandler.render(stack);
    }
}
