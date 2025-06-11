package com.example

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
@SerialName("Consumable")
data class Consumable(
    override val name: String,
    override val description: String,
    val heal: Int = 0,
    val manaRestore: Int = 0
) : Item() {
    fun use(target: Character) {
        target.health += heal
        target.mana += manaRestore
        println("${target.name} usou $name.")
    }
}
