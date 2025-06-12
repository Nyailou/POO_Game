package com.example

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

object ItemModule {
    val module = SerializersModule {
        polymorphic(Item::class) {
            subclass(Consumable::class)
            subclass(Armor::class)
            subclass(Weapon::class)
            subclass(Helmet::class)
            subclass(Chestplate::class)
            subclass(Leggings::class)
            subclass(Boots::class)
        }
    }
}
