package com.example
import kotlinx.serialization.Serializable

@Serializable
class Hero(
    override val name: String,
    override var health: Int,
    override var mana: Int,
    override var strength: Int,
    override var defense: Int,
    override var agility: Int,
    val inventory: Inventory = Inventory(),
    private val equipment: MutableMap<EquipmentSlot, Equipable> = mutableMapOf(),

    // NOVOS CAMPOS:
    var level: Int = 1,
    var experience: Int = 0
) : Character(name, health, mana, strength, defense, agility) {

    fun equip(item: Equipable) {
        val slot = item.slot
        equipment[slot]?.let {
            println("Desequipando ${it.name}...")
            inventory.addItem(it)
            strength -= it.strengthBonus
            defense -= it.defenseBonus
        }

        equipment[slot] = item
        inventory.removeItem(item)
        strength += item.strengthBonus
        defense += item.defenseBonus
        println("${item.name} equipado no slot $slot.")
    }

    fun unequip(slot: EquipmentSlot) {
        val item = equipment.remove(slot)
        if (item != null) {
            inventory.addItem(item)
            strength -= item.strengthBonus
            defense -= item.defenseBonus
            println("${item.name} foi desequipado de $slot.")
        } else {
            println("Nenhum item equipado nesse slot.")
        }
    }

    fun listEquipment() {
        println("== Equipamento Atual ==")
        EquipmentSlot.values().forEach {
            println("$it: ${equipment[it]?.name ?: "Vazio"}")
        }
    }
}
