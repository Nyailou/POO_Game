package com.example
import kotlinx.serialization.Serializable

@Serializable
data class EnemyTemplate(
    val name: String,
    val healthMin: Int,
    val healthMax: Int,
    val manaMin: Int,
    val manaMax: Int,
    val strengthMin: Int,
    val strengthMax: Int,
    val defenseMin: Int,
    val defenseMax: Int,
    val agilityMin: Int,
    val agilityMax: Int,
    val loot: List<Item> = emptyList()
)
