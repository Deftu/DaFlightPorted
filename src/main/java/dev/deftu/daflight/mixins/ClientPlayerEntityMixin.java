package dev.deftu.daflight.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.deftu.daflight.DaFlight;
import dev.deftu.daflight.handlers.MovementHandler;

@Mixin({ClientPlayerEntity.class})
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    public ClientPlayerEntityMixin(ClientWorld clientWorld, GameProfile gameProfile) {
        //#if MC <= 1.19.4
        super(
                clientWorld,
                gameProfile
                //#if MC >= 1.19.2
                , null
                //#endif
        );
        //#else
        //$$ super(clientWorld, gameProfile);
        //#endif
    }

    @Override
    public float getFovMultiplier() {
        return MovementHandler.shouldDisableFov() ? 1f : super.getFovMultiplier();
    }

    @Override
    protected float getJumpVelocity() {
        return MovementHandler.jump(super.getJumpVelocity());
    }

    @Inject(method = "requestRespawn", at = @At("HEAD"))
    private void dfp$onRespawnRequested(CallbackInfo ci) {
        DaFlight.setSingleplayerState(MinecraftClient.getInstance().isInSingleplayer());
        DaFlight.setCurrentServerName(getServerName());
        MovementHandler.reset();
    }

    private static String getServerName() {
        if (!DaFlight.getSingleplayerState()) {
            ServerInfo info = MinecraftClient.getInstance().getCurrentServerEntry();
            return info != null ? info.address.replace(":", "-").replace("-25565", "") : "";
        }

        return "";
    }
}
