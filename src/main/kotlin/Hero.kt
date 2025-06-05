package com.example

class Hero(
    name: String,
    health: Int,
    mana: Int,
    strength: Int,
    defense: Int,
    agility: Int
) : Character(name, health, mana, strength, defense, agility) {
    val inventory = Inventory()
    var experience: Int = 0
}
