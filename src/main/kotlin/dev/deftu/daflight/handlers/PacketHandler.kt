package dev.deftu.daflight.handlers

//#if MC >= 1.20.5
//$$ import io.netty.buffer.ByteBuf
//$$ import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
//$$ import net.minecraft.network.codec.PacketCodec
//$$ import net.minecraft.network.codec.PacketCodecs
//$$ import net.minecraft.network.packet.CustomPayload
//#endif

import dev.deftu.daflight.DaFlight
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking

object PacketHandler {
    //#if MC >= 1.20.5
    //$$ data class SetMaxFlySpeedPayload(val maxFlySpeed: Float) : CustomPayload {
    //$$     companion object {
    //$$
    //$$         val id = CustomPayload.Id<SetMaxFlySpeedPayload>(flyChannel)
    //$$         val codec: PacketCodec<ByteBuf, SetMaxFlySpeedPayload> = PacketCodec.tuple(PacketCodecs.FLOAT, SetMaxFlySpeedPayload::maxFlySpeed, ::SetMaxFlySpeedPayload)
    //$$
    //$$     }
    //$$
    //$$     override fun getId(): CustomPayload.Id<out CustomPayload> {
    //$$         return SetMaxFlySpeedPayload.id
    //$$     }
    //$$ }
    //$$
    //$$ data class SetMaxWalkSpeedPayload(val maxWalkSpeed: Float) : CustomPayload {
    //$$     companion object {
    //$$
    //$$         val id = CustomPayload.Id<SetMaxWalkSpeedPayload>(sprintChannel)
    //$$         val codec: PacketCodec<ByteBuf, SetMaxWalkSpeedPayload> = PacketCodec.tuple(PacketCodecs.FLOAT, SetMaxWalkSpeedPayload::maxWalkSpeed, ::SetMaxWalkSpeedPayload)
    //$$
    //$$     }
    //$$
    //$$     override fun getId(): CustomPayload.Id<out CustomPayload> {
    //$$         return SetMaxWalkSpeedPayload.id
    //$$     }
    //$$ }
    //#endif

    private val flyChannel = DaFlight.identifier(DaFlight.ID, "fly")
    private val sprintChannel = DaFlight.identifier(DaFlight.ID, "sprint")

    fun initialize() {
        //#if MC >= 1.20.5
        //$$ PayloadTypeRegistry.playS2C().register(SetMaxFlySpeedPayload.id, SetMaxFlySpeedPayload.codec)
        //$$ PayloadTypeRegistry.playS2C().register(SetMaxWalkSpeedPayload.id, SetMaxWalkSpeedPayload.codec)
        //$$
        //$$ ClientPlayNetworking.registerGlobalReceiver(SetMaxFlySpeedPayload.id) { payload, ctx ->
        //$$     MovementHandler.maxFlySpeed = payload.maxFlySpeed
        //$$ }
        //$$
        //$$ ClientPlayNetworking.registerGlobalReceiver(SetMaxWalkSpeedPayload.id) { payload, ctx ->
        //$$     MovementHandler.maxWalkSpeed = payload.maxWalkSpeed
        //$$ }
        //#else
        ClientPlayNetworking.registerGlobalReceiver(flyChannel) { _, _, buffer, _ ->
            MovementHandler.maxFlySpeed = buffer.readFloat()
        }

        ClientPlayNetworking.registerGlobalReceiver(sprintChannel) { _, _, buffer, _ ->
            MovementHandler.maxWalkSpeed = buffer.readFloat()
        }
        //#endif
    }
}
