package com.example

open class Character(
    open val name: String,
    open var health: Int,
    open var mana: Int,
    open var strength: Int,
    open var defense: Int,
    open var agility: Int
) {
    // construtor secundário sem parâmetros
    constructor() : this(
        name    = "Unknown",
        health  = 0,
        mana    = 0,
        strength= 0,
        defense = 0,
        agility = 0
    )

    fun attack(target: Character) {
        val damage = (strength - target.defense).coerceAtLeast(0)
        target.health -= damage
        println("$name atacou ${target.name} causando $damage de dano.")
    }

    fun isAlive(): Boolean = health > 0
}
