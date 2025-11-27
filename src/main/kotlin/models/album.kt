package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Album(
    val id: Int? = null,
    val name: String,
    val artist: String,
    val year: Int,
    val imageUrl: String
)