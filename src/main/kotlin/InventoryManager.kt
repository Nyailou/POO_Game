package com.example

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

object InventoryManager {
    private val file = File("main/save/inventory.json")

    private val json = Json {
        serializersModule = ItemModule.module
        classDiscriminator = "type"
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    fun save(inventory: Inventory) {
        file.writeText(json.encodeToString(inventory))
    }

    fun load(): Inventory {
        return if (file.exists()) {
            json.decodeFromString(file.readText())
        } else {
            Inventory()
        }
    }
}
