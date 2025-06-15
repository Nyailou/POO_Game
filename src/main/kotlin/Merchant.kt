package com.example

object Merchant {
    private val ofertasDisponiveis = listOf(
        Weapon("Espada de Bronze", "Uma espada simples", 2, 0),
        Weapon("Adaga Afiada", "Boa para ataques rápidos", 3, 0),
        Helmet("Elmo de Couro", "Protege contra impacto leve", 0, 2),
        Chestplate("Peitoral de Ferro", "Boa proteção para o torso", 0, 4),
        Leggings("Calças de Couro", "Resistência moderada", 0, 2),
        Boots("Botas Resistentes", "Mais agilidade e tração", 0, 1),
        Consumable("Poção de Vida", "Recupera 30 HP", 30, 0),
        Consumable("Poção de Mana", "Recupera 20 de Mana", 0, 20)
    )

    fun visit(hero: Hero) {
        println("\n🧙 Um mercador misterioso apareceu com itens para vender!")

        val ofertas = ofertasDisponiveis.shuffled().take(3)

        ofertas.forEachIndexed { i, item ->
            println("${i + 1} - ${item.name}: ${item.description}")
        }
        println("0 - Não comprar nada")

        val escolha = readLine()?.toIntOrNull() ?: -1
        val itemEscolhido = ofertas.getOrNull(escolha - 1)

        if (itemEscolhido != null) {
            println("Você comprou ${itemEscolhido.name}!")
            hero.inventory.addItem(itemEscolhido)
            InventoryManager.save(hero.inventory)
        } else {
            println("Você decidiu não comprar nada.")
        }
    }
}
