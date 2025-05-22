package com.example

fun main() {
    val hero = Hero("Arthur", 100, 30, 15, 5, 12)
    hero.inventory.addItem(Consumable("Poção de Vida", "Cura 20 HP", heal = 20))
    camp(hero)

    val enemy = Enemy("Goblin", 60, 0, 10, 3, 8, emptyList())

    TurnBasedCombat.start(hero, enemy)
}

fun camp(hero: Hero) {
    println("\nVocê está no acampamento, ${hero.name}.")
    var stayInCamp = true

    while (stayInCamp) {
        println(
            """
            === MENU DO ACAMPAMENTO ===
            1 - Descansar
            2 - Ver inventário
            3 - Adicionar item ao inventário
            4 - Remover item do inventário
            5 - Sair do acampamento (e entrar em combate)
            """.trimIndent()
        )

        when (readLine()?.toIntOrNull()) {
            1 -> {
                hero.health = 100
                hero.mana = 30
                println("Você descansou. Vida e mana restauradas!")
            }

            2 -> {
                println("Inventário:")
                hero.inventory.listItems()
            }

            3 -> {
                println("Digite o nome do item a adicionar:")
                val itemName = readLine() ?: "Item Genérico"
                val item = Consumable(itemName, "Item criado no acampamento", heal = 10)
                hero.inventory.addItem(item)
            }

            4 -> {
                println("Digite o nome do item a remover:")
                val toRemove = readLine()
                val removed = hero.inventory.removeItemByName(toRemove ?: "")
                if (removed) println("Item removido.") else println("Item não encontrado.")
            }

            5 -> {
                stayInCamp = false
                println("Você deixou o acampamento... Um inimigo apareceu!")
                val enemy = Enemy("Goblin", 60, 0, 10, 3, 8, emptyList())
                TurnBasedCombat.start(hero, enemy)

                if (hero.isAlive()) {
                    camp(hero)
                } else {
                    println("⚰️  ${hero.name} foi derrotado em batalha.")
                }
            }

            else -> println("Opção inválida.")
        }
    }
}
