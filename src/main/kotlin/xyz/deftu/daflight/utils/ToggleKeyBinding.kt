package xyz.deftu.daflight.utils

import net.minecraft.client.option.KeyBinding

class ToggleKeyBinding(
    name: String,
    category: String,
    code: Int
) : KeyBinding(
    name,
    code,
    category
) {
    var toggle = false
        private set
        get() {
            val value = field
            field = false
            return value
        }

    fun toggle() {
        toggle = !toggle
    }
}
