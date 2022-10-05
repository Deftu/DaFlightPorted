package xyz.deftu.daflight.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import xyz.deftu.daflight.DaFlight;
import xyz.deftu.daflight.handlers.MovementHandler;

@Mixin({ClientPlayerEntity.class})
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    public ClientPlayerEntityMixin(ClientWorld clientWorld, GameProfile gameProfile) {
        super(clientWorld, gameProfile, null);
    }

    public float getFovMultiplier() {
        return MovementHandler.shouldDisableFov() ? 1f : super.getFovMultiplier();
    }

    public void requestRespawn() {
        super.requestRespawn();
        DaFlight.setSingleplayerState(MinecraftClient.getInstance().isInSingleplayer());
        DaFlight.setCurrentServerName(getServerName());
        MovementHandler.reset();
    }

    protected float getJumpVelocity() {
        return MovementHandler.jump(super.getJumpVelocity());
    }

    private static String getServerName() {
        if (!DaFlight.getSingleplayerState()) {
            ServerInfo info = MinecraftClient.getInstance().getCurrentServerEntry();
            return info != null ? info.address.replace(":", "-").replace("-25565", "") : "";
        }

        return "";
    }
}
