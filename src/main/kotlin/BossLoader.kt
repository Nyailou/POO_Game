package com.example

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

object BossLoader {
    private val json = Json {
        serializersModule = ItemModule.module
        classDiscriminator = "type"
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    private val bosses: List<EnemyTemplate> by lazy {
        val file = File("main/data/bosses.json")
        if (!file.exists()) error("Arquivo de bosses não encontrado.")
        val content = file.readText()
        json.decodeFromString(content)
    }

    fun generateBoss(heroLevel: Int): Enemy {
        val template = bosses.random()
        val multiplier = 1 + (heroLevel - 1) * 0.15

        fun scale(min: Int, max: Int): Int {
            val base = (min..max).random()
            return (base * multiplier).toInt()
        }

        return Enemy(
            name = template.name,
            health = scale(template.healthMin, template.healthMax),
            mana = scale(template.manaMin, template.manaMax),
            strength = scale(template.strengthMin, template.strengthMax),
            defense = scale(template.defenseMin, template.defenseMax),
            agility = scale(template.agilityMin, template.agilityMax),
            loot = template.loot.toList()
        )
    }
}
