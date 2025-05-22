package com.example

class Consumable(
    name: String,
    description: String,
    val heal: Int = 0,
    val manaRestore: Int = 0
) : Item(name, description) {
    fun use(target: Character) {
        target.health += heal
        target.mana += manaRestore
        println("${target.name} usou $name.")
    }
}
