package com.example

class Inventory {
    var items = mutableListOf<Item>()

    fun addItem(item: Item) {
        items.add(item)
        println("${item.name} adicionado ao inventário.")
    }

    fun listItems() = items.forEach { println("- ${it.name}") }

    fun removeItemByName(name: String): Boolean {
        val item = items.find { it.name.equals(name, ignoreCase = true) }
        return if (item != null) {
            items.remove(item)
            true
        } else {
            false
        }
    }
}
