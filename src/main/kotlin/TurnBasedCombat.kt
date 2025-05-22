package com.example

object TurnBasedCombat {
    fun start(hero: Hero, enemy: Enemy) {
        println("Início da batalha: ${hero.name} vs ${enemy.name}")

        while (hero.isAlive() && enemy.isAlive()) {
            val playerTurn = hero.agility >= enemy.agility
            if (playerTurn) {
                playerAction(hero, enemy)
                if (enemy.isAlive()) enemyTurn(enemy, hero)
            } else {
                enemyTurn(enemy, hero)
                if (hero.isAlive()) playerAction(hero, enemy)
            }
        }

        val winner = if (hero.isAlive()) hero.name else enemy.name
        println("A batalha acabou! Vencedor: $winner")
    }

    private fun playerAction(hero: Hero, enemy: Enemy) {
        println("\nTurno de ${hero.name}. Escolha uma ação:")
        println("1 - Atacar")
        println("2 - Usar habilidade")
        println("3 - Usar item")
        println("4 - Fugir e ir para o acampamento")

        when (readLine()?.toIntOrNull()) {
            1 -> hero.attack(enemy)

            2 -> {
                val fireball = Skill("Bola de Fogo", manaCost = 10, power = 25)
                fireball.use(hero, enemy)
            }

            3 -> {
                val potion = hero.inventory.run {
                    listItems()
                    items.firstOrNull { it is Consumable } as? Consumable
                }

                if (potion != null) {
                    potion.use(hero)
                } else {
                    println("Nenhum consumível disponível.")
                }
            }

            4 -> {
                println("${hero.name} fugiu da batalha!")
                camp(hero)
                return
            }

            else -> {
                println("Ação inválida. Você perdeu o turno.")
            }
        }
    }

    private fun enemyTurn(enemy: Enemy, hero: Hero) {
        println("\nTurno de ${enemy.name}")
        enemy.attack(hero)
    }
}
