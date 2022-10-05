package xyz.deftu.daflight

import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config

@Config(name = DaFlight.ID)
class DaFlightConfig : ConfigData {
    var toggle = true

    var hud = true

    var disableFov = true
    var strafeModifier = 0.0
    var verticalModifier = 0.0
    var flyBoost = 0.0
    var flySpeed = 0f
    var flight3d = false
    var sprintBoost = 0.0
    var sprintSpeed = 0f
    var jumpModifier = 0f
}
