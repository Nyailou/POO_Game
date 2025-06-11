package com.example
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Weapon")
data class Weapon(
    override val name: String,
    override val description: String,
    val attackBonus: Int
) : Item()
