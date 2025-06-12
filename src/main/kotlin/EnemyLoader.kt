package com.example

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

object EnemyLoader {
    private val json = Json {
        serializersModule = ItemModule.module
        classDiscriminator = "type"
        ignoreUnknownKeys = true
    }

    private val templates: List<EnemyTemplate> by lazy {
        val file = File("main/data/enemies.json")
        if (!file.exists()) error("Arquivo de inimigos não encontrado.")
        val content = file.readText()
        json.decodeFromString(content)
    }

    fun generateEnemyFromTemplates(heroLevel: Int): Enemy {
        val template = templates.random()

        val multiplier = 1 + (heroLevel - 1) * 0.1

        fun scale(rangeMin: Int, rangeMax: Int): Int {
            val base = (rangeMin..rangeMax).random()
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
