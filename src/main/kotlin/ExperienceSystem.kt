package com.example

object ExperienceSystem {
    fun gainXp(hero: Hero, xp: Int) {
        hero.experience += xp
        println("${hero.name} ganhou $xp de XP.")

        val requiredXp = hero.level * 100

        while (hero.experience >= requiredXp) {
            hero.experience -= requiredXp
            hero.level++

            // Bônus por nível
            hero.health += 10
            hero.mana += 5
            hero.strength += 2
            hero.defense += 2
            hero.agility += 1

            println("${hero.name} subiu para o nível ${hero.level}!")
            println("+10 HP, +5 Mana, +2 Força, +2 Defesa, +1 Agilidade")
        }
    }
}
