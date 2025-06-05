package com.example
import kotlin.system.exitProcess

fun main() {
    val hero = Hero("Jogador", 100, 50, 20, 10, 5)
    hero.inventory.addItem(Consumable("Poção de Vida", "Cura 30 HP", heal = 30))
    val skill = Skill("Golpe Flamejante", 10, 25)
    val camp = Camp()

    println("Bem-vindo ao seu acampamento, ${hero.name}!")

    while (hero.isAlive()) {
        camp.menu(hero, ::explore)
    }

    println("Você morreu... Fim de jogo.")
}


class Camp {
    private val chest = mutableListOf<Item>()

    fun rest(hero: Hero) {
        hero.health = 100
        hero.mana = 50
        println("${hero.name} descansou. Vida e mana restauradas!")
    }

    fun storeItem(hero: Hero) {
        val items = hero.inventory.getItems()
        if (items.isEmpty()) {
            println("Você não tem itens para guardar.")
            return
        }

        println("Escolha um item para guardar:")
        items.forEachIndexed { i, item -> println("${i + 1} - ${item.name}") }
        val index = readLine()?.toIntOrNull()?.minus(1)
        val item = items.getOrNull(index ?: -1)

        if (item != null) {
            chest.add(item)
            hero.inventory.removeItem(item)
            println("${item.name} armazenado no baú.")
        } else {
            println("Opção inválida.")
        }
    }

    fun retrieveItem(hero: Hero) {
        if (chest.isEmpty()) {
            println("O baú está vazio.")
            return
        }

        println("Escolha um item para retirar:")
        chest.forEachIndexed { i, item -> println("${i + 1} - ${item.name}") }
        val index = readLine()?.toIntOrNull()?.minus(1)
        val item = chest.getOrNull(index ?: -1)

        if (item != null) {
            hero.inventory.addItem(item)
            chest.remove(item)
            println("${item.name} adicionado ao inventário.")
        } else {
            println("Opção inválida.")
        }
    }

    fun menu(hero: Hero, onExplore: (Hero) -> Unit) {
        while (hero.isAlive()) {
            println("\n=== Acampamento ===")
            println("1 - Descansar")
            println("2 - Armazenar item")
            println("3 - Retirar item")
            println("4 - Explorar")
            println("5 - Sair do jogo")

            when (readLine()) {
                "1" -> rest(hero)
                "2" -> storeItem(hero)
                "3" -> retrieveItem(hero)
                "4" -> {
                    println("Partindo para explorar...")
                    onExplore(hero)
                }
                "5" -> {
                    println("Encerrando o jogo. Até a próxima!")
                    exitProcess(0)
                }
                else -> println("Opção inválida.")
            }
        }
    }
}

fun explore(hero: Hero) {
    val skill = Skill("Golpe Flamejante", 10, 25)
    val enemy = Enemy(
        name = "Goblin",
        health = 70,
        mana = 20,
        strength = 15,
        defense = 5,
        agility = 3,
        loot = listOf(Consumable("Poção Fraca", "Cura 15 HP", heal = 15))
    )

    println("Você encontrou um ${enemy.name}!")

    while (hero.isAlive() && enemy.isAlive()) {
        println("\n${hero.name} HP: ${hero.health}, Mana: ${hero.mana}")
        println("${enemy.name} HP: ${enemy.health}")
        println("1 - Atacar")
        println("2 - Usar item")
        println("3 - Usar habilidade")
        println("4 - Fugir")

        when (readLine()) {
            "1" -> hero.attack(enemy)
            "2" -> {
                val consumables = hero.inventory.getItems().filterIsInstance<Consumable>()
                if (consumables.isEmpty()) println("Nenhum item utilizável.")
                else {
                    consumables.forEachIndexed { i, item -> println("${i + 1} - ${item.name}") }
                    val idx = readLine()?.toIntOrNull()?.minus(1)
                    val item = consumables.getOrNull(idx ?: -1)
                    item?.use(hero) ?: println("Opção inválida.")
                }
            }
            "3" -> skill.use(hero, enemy)
            "4" -> {
                println("Você fugiu de volta ao acampamento.")
                return
            }
            else -> println("Inválido.")
        }

        if (enemy.isAlive()) enemy.attack(hero)
    }

    if (hero.isAlive()) {
        println("Você venceu o ${enemy.name}!")
        ExperienceSystem.gainXp(hero, 100)
        enemy.loot.forEach { hero.inventory.addItem(it) }
    } else {
        println("Você foi derrotado...")
    }

    println("Retornando ao acampamento...")
}
