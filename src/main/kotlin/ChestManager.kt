package com.example

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

object ChestManager {
    private val file = File("main/save/chest.json")
    private val json = Json {
        serializersModule = ItemModule.module
        classDiscriminator = "type"
        prettyPrint = true
    }

    private fun ensureDir() {
        file.parentFile.takeIf { !it.exists() }?.mkdirs()
    }

    fun save(chest: List<Item>) {
        val json = Json {
            serializersModule = ItemModule.module
            classDiscriminator = "type"
            prettyPrint = true
        }
        file.writeText(json.encodeToString(chest))
    }

    fun load(): MutableList<Item> {
        val json = Json {
            serializersModule = ItemModule.module
            classDiscriminator = "type"
            ignoreUnknownKeys = true
        }
        return if (file.exists()) {
            json.decodeFromString(file.readText())
        } else mutableListOf()
    }
}
