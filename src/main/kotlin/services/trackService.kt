package com.example.services

import com.example.models.Song
import com.example.repositories.SongRepository


class SongService(private val repository: SongRepository) {
    fun createSong(song: Song): Song = repository.create(song)
    fun getAllSongs(): List<Song> = repository.findAll()
    fun getSongById(id: Int): Song? = repository.findById(id)
    fun updateSong(id: Int, song: Song): Boolean = repository.update(id, song)
    fun deleteSong(id: Int): Boolean = repository.delete(id)
}