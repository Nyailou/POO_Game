package com.example
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
@SerialName("Item")
sealed class Item {
    abstract val name: String
    abstract val description: String
}
