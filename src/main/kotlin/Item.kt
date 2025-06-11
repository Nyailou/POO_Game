package com.example
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
@SerialName("Item")
@Polymorphic
abstract class Item {
    abstract val name: String
    abstract val description: String
}
