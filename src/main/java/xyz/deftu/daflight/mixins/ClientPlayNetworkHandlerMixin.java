package xyz.deftu.daflight.mixins;

//#if MC <= 1.20.1
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
//#else
//$$ import net.fabricmc.fabric.impl.networking.payload.UntypedPayload;
//$$ import net.minecraft.network.packet.CustomPayload;
//#endif

import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.deftu.daflight.handlers.PacketHandler;

@Mixin({ClientPlayNetworkHandler.class})
public class ClientPlayNetworkHandlerMixin {
    @Inject(method = "onCustomPayload", at = @At("RETURN"))
    private void dfp$handleCustomPayload(
            //#if MC <= 1.20.1
            CustomPayloadS2CPacket packet,
            //#else
            //$$ CustomPayload packet,
            //#endif
            CallbackInfo ci
    ) {
        //#if MC <= 1.20.1
        PacketHandler.handle(packet.getChannel(), packet.getData());
        //#else
        //$$ if (packet instanceof UntypedPayload payload) {
        //$$     PacketHandler.handle(packet.comp_1678(), payload.buffer());
        //$$ }
        //#endif
    }
}
