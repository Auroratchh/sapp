package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Song(
    val id: Int? = null,
    val name: String,
    val album: String,
    val duration: String,
    val imageUrl: String
)