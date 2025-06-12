package com.example

import kotlinx.serialization.Serializable

@Serializable
data class CampState(
    val chest: MutableList<Item> = mutableListOf()
)
