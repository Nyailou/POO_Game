package com.example

class Enemy(
    name: String,
    health: Int,
    mana: Int,
    strength: Int,
    defense: Int,
    agility: Int,
    val loot: List<Item>
) : Character(name, health, mana, strength, defense, agility)
