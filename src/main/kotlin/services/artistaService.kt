package com.example.services

import com.example.models.Artist
import com.example.repositories.ArtistRepository


class ArtistService(private val repository: ArtistRepository) {
    fun createArtist(artist: Artist): Artist = repository.create(artist)
    fun getAllArtists(): List<Artist> = repository.findAll()
    fun getArtistById(id: Int): Artist? = repository.findById(id)
    fun updateArtist(id: Int, artist: Artist): Boolean = repository.update(id, artist)
    fun deleteArtist(id: Int): Boolean = repository.delete(id)
}