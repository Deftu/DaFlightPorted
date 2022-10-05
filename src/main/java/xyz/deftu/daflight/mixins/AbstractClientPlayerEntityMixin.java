package xyz.deftu.daflight.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import xyz.deftu.daflight.DaFlight;
import xyz.deftu.daflight.handlers.MovementHandler;
import xyz.deftu.daflight.utils.Rotation;
import xyz.deftu.daflight.utils.Vector3D;

@Mixin({AbstractClientPlayerEntity.class})
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity {
    @Unique
    private final Vector3D dfp$heading = new Vector3D();
    @Unique
    private final Rotation dfp$rotation = new Rotation();

    public AbstractClientPlayerEntityMixin(World world) {
        super(world, new BlockPos(0, 0, 0), 0, new GameProfile(null, null), null);
    }

    public void move(MovementType type, Vec3d vec) {
        PlayerEntity player = this;
        if (player == MinecraftClient.getInstance().player) {
            dfp$updateFlyStatus();
            dfp$heading.set(vec.x, vec.y, vec.z);
            dfp$rotation.set(getPitch(), getYaw());
            MovementHandler.setMovement(DaFlight.getPlayerForwardMovement(), DaFlight.getPlayerStrafeMovement());
            MovementHandler.applyMovement(dfp$heading, dfp$rotation);
            super.move(type, new Vec3d(dfp$heading.getX(), dfp$heading.getY(), dfp$heading.getZ()));
        } else super.move(type, vec);
    }

    @Unique
    private void dfp$updateFlyStatus() {
        if (!DaFlight.isPlayerPresent() || !DaFlight.getPlayer().getAbilities().flying)
            return;

        DaFlight.getPlayer().getAbilities().flying = true;
        DaFlight.getPlayer().sendAbilitiesUpdate();
    }
}
