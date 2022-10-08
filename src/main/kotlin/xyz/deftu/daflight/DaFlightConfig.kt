package xyz.deftu.daflight

import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer
import xyz.deftu.daflight.utils.FlightMode

@Config(name = DaFlight.ID)
class DaFlightConfig : ConfigData {
    var toggle = true
    var hud = true
    var flyMode = FlightMode.NORMAL

    @ConfigEntry.Gui.Excluded
    var disableFov = true

    @ConfigEntry.Gui.CollapsibleObject
    var input = Input()

    var strafeModifier = 1f
    var verticalModifier = 1f
    var jumpModifier = 1f

    var flyBoost = 2f
    var flySpeed = 1f

    var sprintBoost = 2f
    var sprintSpeed = 1f

    class Input {
        var flyKeyBind = true
        var sprintKeyBind = true
        var boostKeyBind = true
        var flyUpKeyBind = true
        var flyDownKeyBind = true
    }
}

internal object DaFlightConfigBootstrap {
    private var registered = false

    fun register() {
        if (registered)
            throw UnsupportedOperationException("Config has already been registered!")

        AutoConfig.register(DaFlightConfig::class.java) { config, clz ->
            GsonConfigSerializer(config, clz)
        }

        registered = true
    }
}
