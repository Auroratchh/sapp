package com.example.services

import com.example.models.Album
import com.example.repositories.AlbumRepository


class AlbumService(private val repository: AlbumRepository) {
    fun createAlbum(album: Album): Album = repository.create(album)
    fun getAllAlbums(): List<Album> = repository.findAll()
    fun getAlbumById(id: Int): Album? = repository.findById(id)
    fun updateAlbum(id: Int, album: Album): Boolean = repository.update(id, album)
    fun deleteAlbum(id: Int): Boolean = repository.delete(id)
}