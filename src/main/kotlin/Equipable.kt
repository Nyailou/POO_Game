package com.example

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
@SerialName("Equipable")
sealed class Equipable : Item() {
    abstract val slot: EquipmentSlot
    abstract val strengthBonus: Int
    abstract val defenseBonus: Int
}