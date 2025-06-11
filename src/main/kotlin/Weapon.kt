package com.example
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Weapon")
data class Weapon(
    override val name: String,
    override val description: String,
    override val strengthBonus: Int,
    override val defenseBonus: Int = 0
) : Equipable() {
    override val slot = EquipmentSlot.WEAPON
}
