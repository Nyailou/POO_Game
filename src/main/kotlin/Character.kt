package com.example

open class Character(
    val name: String,
    var health: Int,
    var mana: Int,
    var strength: Int,
    var defense: Int,
    var agility: Int,
    var level: Int = 1
) {
    open fun attack(target: Character) {
        val damage = (strength - target.defense).coerceAtLeast(0)
        target.health -= damage
        println("$name atacou ${target.name} causando $damage de dano.")
    }

    fun isAlive(): Boolean = health > 0
}
