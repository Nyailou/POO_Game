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

    fun visitar(hero: Hero) {
        if (hero.jaVisitouMercador) {
            println("Você já visitou o mercador nesta rodada.")
            return
        }

        if (hero.inimigosDerrotadosUltimaExploracao < 3) {
            println("Você precisa derrotar pelo menos 3 inimigos na última exploração para acessar o mercador.")
            return
        }

        println("\nO mercador mostra três itens raros:")

        val ofertas = ofertasDisponiveis.shuffled().take(3)

        ofertas.forEachIndexed { i, item ->
            println("${i + 1} - ${item.name}: ${item.description}")
        }
        println("0 - Não comprar nada")

        val escolha = readLine()?.toIntOrNull() ?: -1
        val itemEscolhido = ofertas.getOrNull(escolha - 1)

        if (itemEscolhido != null) {
            println("Você adquiriu ${itemEscolhido.name}!")
            hero.inventory.addItem(itemEscolhido)
            InventoryManager.save(hero.inventory)
            hero.jaVisitouMercador = true
        } else {
            println("Você recusou a oferta do mercador.")
        }
    }
}
