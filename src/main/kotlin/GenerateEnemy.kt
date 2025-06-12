import com.example.Consumable
import com.example.Enemy

fun generateEnemy(heroLevel: Int): Enemy {
    val baseHealth = (60..100).random()
    val baseMana = (10..30).random()
    val baseStrength = (10..20).random()
    val baseDefense = (5..15).random()
    val baseAgility = (2..6).random()

    // Escalonamento por nível
    val levelMultiplier = 1 + (heroLevel - 1) * 0.1

    val scaledHealth = (baseHealth * levelMultiplier).toInt()
    val scaledMana = (baseMana * levelMultiplier).toInt()
    val scaledStrength = (baseStrength * levelMultiplier).toInt()
    val scaledDefense = (baseDefense * levelMultiplier).toInt()
    val scaledAgility = baseAgility + (heroLevel / 5)

    return Enemy(
        name = listOf("Goblin", "Esqueleto", "Bandido").random(),
        health = scaledHealth,
        mana = scaledMana,
        strength = scaledStrength,
        defense = scaledDefense,
        agility = scaledAgility,
        loot = listOf(
            Consumable("Poção Fraca", "Cura 15 HP", heal = 15)
        )
    )
}
