package xyz.deftu.daflight

import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer
import me.shedaniel.clothconfig2.api.ConfigBuilder
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.text.Text

object DaFlight : ClientModInitializer {
    const val NAME = "@MOD_NAME@"
    const val ID = "@MOD_ID@"

    private var initialized = false
    @JvmStatic
    val config: DaFlightConfig
        get() {
            if (!initialized)
                throw IllegalStateException("Calling config too early!")

            return AutoConfig.getConfigHolder(DaFlightConfig::class.java).config
        }

    @JvmStatic
    val player: ClientPlayerEntity?
        get() = MinecraftClient.getInstance().player

    @JvmStatic
    var singleplayerState = false
    @JvmStatic
    var currentServerName = ""

    @JvmStatic
    lateinit var flyKeyBind: KeyBinding
        private set
    @JvmStatic
    lateinit var sprintKeyBind: KeyBinding
        private set
    @JvmStatic
    lateinit var boostKeyBind: KeyBinding
        private set
    @JvmStatic
    lateinit var flyUpKeyBind: KeyBinding
        private set
    @JvmStatic
    lateinit var flyDownKeyBind: KeyBinding
        private set

    override fun onInitializeClient() {
        AutoConfig.register(DaFlightConfig::class.java) { config, clz ->
            GsonConfigSerializer(config, clz)
        }

        flyKeyBind = KeyBinding("Fly", InputUtil.GLFW_KEY_G, NAME).also(KeyBindingHelper::registerKeyBinding)
        sprintKeyBind = KeyBinding("Sprint", InputUtil.GLFW_KEY_R, NAME).also(KeyBindingHelper::registerKeyBinding)
        boostKeyBind = KeyBinding("Fly boost", InputUtil.GLFW_KEY_R, NAME).also(KeyBindingHelper::registerKeyBinding)
        flyUpKeyBind = KeyBinding("Fly up", InputUtil.GLFW_KEY_UP, NAME).also(KeyBindingHelper::registerKeyBinding)
        flyDownKeyBind = KeyBinding("Fly down", InputUtil.GLFW_KEY_DOWN, NAME).also(KeyBindingHelper::registerKeyBinding)

        initialized = true
    }

    @JvmStatic
    fun isPlayerPresent() = player != null
    @JvmStatic
    fun isGameFocused() = MinecraftClient.getInstance().isPaused && MinecraftClient.getInstance().isWindowFocused
    @JvmStatic
    fun getPlayerForwardMovement() = if (isPlayerPresent()) player!!.input.movementForward else 0f
    @JvmStatic
    fun getPlayerStrafeMovement() = if (isPlayerPresent()) player!!.input.movementSideways else 0f
}
