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
        ensureDir()
        // serializa a lista de Item usando o mesmo json
        file.writeText(json.encodeToString(chest))
    }

    fun load(): MutableList<Item> {
        ensureDir()
        return if (file.exists()) {
            // desserializa polimorficamente
            json.decodeFromString<List<Item>>(file.readText()).toMutableList()
        } else {
            mutableListOf()
        }
    }
}
