package com.example
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Helmet")
data class Helmet(
    override val name: String,
    override val description: String,
    override val strengthBonus: Int = 0,
    override val defenseBonus: Int
) : Equipable() {
    override val slot = EquipmentSlot.HELMET
}
