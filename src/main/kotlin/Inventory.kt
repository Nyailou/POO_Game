package com.example
import kotlinx.serialization.Serializable

@Serializable
class Inventory {
    private val items = mutableListOf<Item>()

    fun addItem(item: Item) {
        items.add(item)
        println("${item.name} adicionado ao inventário.")
    }

    fun listItems() = items.forEach { println("- ${it.name}") }

    fun getItems(): List<Item> = items

    fun removeItem(item: Item) {
        if (items.remove(item)) {
            println("${item.name} removido do inventário.")
        }
    }
}
