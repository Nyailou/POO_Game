package com.example

class Weapon(
    name: String,
    description: String,
    val attackBonus: Int
) : Item(name, description)
