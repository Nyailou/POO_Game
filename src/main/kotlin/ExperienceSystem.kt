package com.example

object ExperienceSystem {
    fun gainXp(hero: Hero, xp: Int) {
        hero.experience += xp
        println("${hero.name} ganhou $xp de XP.")
        val requiredXp = hero.level * 100
        if (hero.experience >= requiredXp) {
            hero.level++
            hero.experience = 0
            hero.health += 10
            hero.strength += 2
            println("${hero.name} subiu para o nível ${hero.level}!")
        }
    }
}
