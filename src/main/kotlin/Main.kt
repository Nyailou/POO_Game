package com.example
import kotlin.system.exitProcess
import generateEnemy

fun startGameLoop(hero: Hero, camp: Camp) {
    println("Bem-vindo ao seu acampamento, ${hero.name}!")

    while (hero.isAlive()) {
        camp.menu(hero, ::explore)
        SaveManager.save(hero)
    }

    println("Você morreu... Fim de jogo.")
    SaveManager.delete()
}


fun main() {
    val camp = Camp()
    var hero: Hero?

    while (true) {
        println("\n=== Menu Inicial ===")
        println("1 - Criar novo personagem")
        println("2 - Continuar com personagem existente")
        println("3 - Eliminar personagem")
        println("4 - Sair do jogo")

        when (readLine()) {
            "1" -> {
                println("Nome do herói:")
                val name = readLine() ?: "Jogador"
                hero = Hero(name, 100, 50, 20, 10, 5)
                hero.inventory.addItem(Consumable("Poção de Vida", "Cura 30 HP", heal = 30))
                SaveManager.save(hero)
                println("Personagem criado e salvo com sucesso!")
                startGameLoop(hero, camp)
            }
            "2" -> {
                hero = SaveManager.load()
                if (hero != null) {
                    println("Personagem ${hero.name} carregado com sucesso.")
                    startGameLoop(hero, camp)
                } else {
                    println("Nenhum personagem salvo encontrado.")
                }
            }
            "3" -> {
                SaveManager.delete()
                println("Personagem eliminado.")
            }
            "4" -> {
                println("Até à próxima!")
                exitProcess(0)
            }
            else -> println("Opção inválida.")
        }
    }
}

class Camp {
    private val chest = ChestManager.load()

    fun rest(hero: Hero) {
        hero.health = 100
        hero.mana = 50
        println("${hero.name} descansou. Vida e mana restauradas!")
        SaveManager.save(hero)
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
            ChestManager.save(chest)
            SaveManager.save(hero)
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
            ChestManager.save(chest)
            SaveManager.save(hero)
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
            println("5 - Equipar item")
            println("6 - Desequipar item")
            println("7 - Sair do jogo")

            when (readLine()) {
                "1" -> rest(hero)
                "2" -> storeItem(hero)
                "3" -> retrieveItem(hero)
                "4" -> {
                    println("Partindo para explorar...")
                    onExplore(hero)
                }
                "5" -> {
                    val equipables = hero.inventory.getItems().filterIsInstance<Equipable>()
                    if (equipables.isEmpty()) {
                        println("Nenhum equipamento disponível.")
                    } else {
                        println("Escolha um item para equipar:")
                        equipables.forEachIndexed { i, item -> println("${i + 1} - ${item.name} (${item.slot})") }
                        val idx = readLine()?.toIntOrNull()?.minus(1)
                        val item = equipables.getOrNull(idx ?: -1)
                        if (item != null) hero.equip(item) else println("Opção inválida.")
                    }
                }
                "6" -> {
                    println("Escolha o slot para desequipar:")
                    EquipmentSlot.values().forEachIndexed { i, slot -> println("${i + 1} - $slot") }
                    val idx = readLine()?.toIntOrNull()?.minus(1)
                    val slot = EquipmentSlot.values().getOrNull(idx ?: -1)
                    if (slot != null) hero.unequip(slot) else println("Opção inválida.")
                }
                "7" -> {
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
    val lootColetado = mutableListOf<Item>()
    var xpGanho = 0
    var encontros = 0
    val maxEncontros = 10

    println("Você partiu para a exploração!")

    while (hero.isAlive()) {
        encontros++
        val isBoss = encontros > maxEncontros

        val enemy = if (isBoss) {
            Enemy(
                name = "Dragão das Sombras",
                health = 250,
                mana = 100,
                strength = 35,
                defense = 20,
                agility = 10,
                loot = listOf(
                    Consumable("Poção Mística", "Cura 100 HP e 50 Mana", heal = 100, manaRestore = 50),
                    Consumable("Elixir do Poder", "Cura total", heal = 999)
                )
            )
        } else {
            EnemyLoader.generateEnemyFromTemplates(hero.level)
        }

        println("\nVocê encontrou um ${enemy.name}!")

        combate@ while (hero.isAlive() && enemy.isAlive()) {
            println("\n${hero.name} HP: ${hero.health}, Mana: ${hero.mana}")
            println("${enemy.name} HP: ${enemy.health}")
            println("1 - Atacar")
            println("2 - Usar item")
            println("3 - Usar habilidade")
            if (!isBoss) println("4 - Desistir da exploração (sem recompensas)")
            if (!isBoss) println("5 - Fugir da luta (continua explorando)")

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
                    if (!isBoss) {
                        println("Você desistiu da exploração e retorna de mãos vazias.")
                        return
                    } else println("Você não pode desistir contra o boss!")
                }
                "5" -> {
                    if (!isBoss) {
                        println("Você fugiu dessa luta, mas continua a explorar.")
                        break@combate
                    } else println("Você não pode fugir do boss!")
                }
                else -> println("Opção inválida.")
            }

            if (enemy.isAlive()) {
                enemy.attack(hero)
            }
        }

        if (!hero.isAlive()) {
            println("Você morreu durante a exploração. Fim de jogo.")
            return
        }

        if (!enemy.isAlive()) {
            println("Você derrotou o ${enemy.name}!")

            if (isBoss) {
                xpGanho += 500
                lootColetado.addAll(enemy.loot)
                println("Parabéns! Você derrotou o boss final.")
                break
            } else {
                xpGanho += 100
                lootColetado.addAll(enemy.loot)
            }
        }

        if (!isBoss) {
            println("\nDeseja continuar explorando?")
            println("1 - Sim")
            println("2 - Encerrar exploração e voltar ao acampamento com recompensas")

            when (readLine()) {
                "2" -> {
                    println("Você retornou com sucesso ao acampamento!")
                    println("XP ganho: $xpGanho")
                    ExperienceSystem.gainXp(hero, xpGanho)

                    println("Loot coletado:")
                    lootColetado.forEach {
                        println("- ${it.name}")
                        hero.inventory.addItem(it)
                    }
                    return
                }
                "1" -> continue
                else -> println("Opção inválida. Continuando por padrão.")
            }
        }
    }

    // Se chegar aqui, venceu o boss
    println("Você retornou ao acampamento com grande glória!")
    println("XP ganho: $xpGanho")
    ExperienceSystem.gainXp(hero, xpGanho)

    println("Loot coletado:")
    lootColetado.forEach {
        println("- ${it.name}")
        hero.inventory.addItem(it)
    }
}
