package com.example

var currentHero: Hero? = null

fun main() {
    var running = true

    while (running) {
        println(
            """
        === MENU INICIAL ===
        ${if (currentHero == null) "Nenhum personagem criado" else "Personagem atual: ${currentHero?.name}"}
        
        1 - Criar personagem
        2 - Apagar personagem
        3 - Entrar no jogo
        4 - Sair
        """.trimIndent()
        )

        when (readLine()?.toIntOrNull()) {
            1 -> createHero()
            2 -> deleteHero()
            3 -> {
                if (currentHero == null) {
                    println("❌ Crie um personagem antes de entrar no jogo.")
                } else {
                    camp(currentHero!!)
                }
            }
            4 -> {
                println("👋 Saindo do jogo. Até a próxima!")
                running = false
            }
            else -> println("Opção inválida.")
        }
    }
}

//val hero = Hero("Arthur", 100, 30, 15, 5, 12)
//hero.inventory.addItem(Consumable("Poção de Vida", "Cura 20 HP", heal = 20))
//camp(hero)

//val enemy = Enemy("Goblin", 60, 0, 10, 3, 8, emptyList())

//TurnBasedCombat.start(hero, enemy)

fun createHero() {
    if (currentHero != null) {
        println("❌ Já existe um personagem criado.")
        return
    }

    println("Digite o nome do seu herói:")
    val name = readLine()?.trim().takeIf { !it.isNullOrEmpty() } ?: "Herói"

    currentHero = Hero(
        name = name,
        health = 100,
        mana = 30,
        strength = 15,
        defense = 5,
        agility = 10
    )

    currentHero!!.inventory.addItem(Consumable("Poção de Vida", "Cura 20 HP", heal = 20))
    println("✅ Personagem $name criado com sucesso!")
}

fun deleteHero() {
    if (currentHero == null) {
        println("⚠️ Nenhum personagem para apagar.")
    } else {
        println("Tem certeza que deseja apagar ${currentHero!!.name}? (s/n)")
        val confirm = readLine()
        if (confirm.equals("s", ignoreCase = true)) {
            currentHero = null
            println("✅ Personagem apagado.")
        } else {
            println("❌ Ação cancelada.")
        }
    }
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
