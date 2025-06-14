package com.example

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

object SaveManager {
    private val file = File("main/save/hero.json")
    private val json = Json {
        serializersModule = ItemModule.module
        classDiscriminator = "type"
        prettyPrint = true
    }

    private fun ensureDir() {
        file.parentFile.takeIf { !it.exists() }?.mkdirs()
    }

    fun save(hero: Hero) {
        ensureDir()
        file.writeText(json.encodeToString(hero))
    }

    fun load(): Hero? {
        return if (file.exists()) {
            json.decodeFromString<Hero>(file.readText())
        } else null
    }

    fun delete() {
        if (file.exists()) file.delete()
    }
}
