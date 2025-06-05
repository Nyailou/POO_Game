package com.example

class Skill(
    val skillName: String,
    val manaCost: Int,
    val power: Int
) {
    fun use(user: Character, target: Character) {
        if (user.mana >= manaCost) {
            user.mana -= manaCost
            val damage = (power + user.strength - target.defense).coerceAtLeast(0)
            target.health -= damage
            println("${user.name} usou $skillName em ${target.name} causando $damage de dano.")
        } else {
            println("${user.name} não tem mana suficiente para usar $skillName.")
        }
    }
}
