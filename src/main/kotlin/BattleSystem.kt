package com.example

object BattleSystem {
    fun fight(hero: Hero, enemy: Enemy) {
        while (hero.isAlive() && enemy.isAlive()) {
            hero.attack(enemy)
            if (enemy.isAlive()) enemy.attack(hero)
        }
        println("Batalha encerrada. Vencedor: ${if (hero.isAlive()) hero.name else enemy.name}")
    }
}
