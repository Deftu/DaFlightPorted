package xyz.deftu.daflight.mixins;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.deftu.daflight.handlers.PacketHandler;

@Mixin({ClientPlayNetworkHandler.class})
public class ClientPlayNetworkHandlerMixin {
    @Inject(method = "onCustomPayload", at = @At("RETURN"))
    private void dfp$handleCustomPayload(CustomPayloadS2CPacket packet, CallbackInfo ci) {
        PacketHandler.handle(packet.getChannel(), packet.getData());
    }
}
