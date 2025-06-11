package com.example

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
@SerialName("Armor")
data class Armor(
    override val name: String,
    override val description: String,
    val defenseBonus: Int
) : Item()
