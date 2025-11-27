package com.example.repositories

import com.example.database.Songs
import com.example.models.Song
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class SongRepository {
    fun create(song: Song): Song = transaction {
        val id = Songs.insert {
            it[name] = song.name
            it[album] = song.album
            it[duration] = song.duration
            it[imageUrl] = song.imageUrl
        }[Songs.id]
        song.copy(id = id)
    }

    fun findAll(): List<Song> = transaction {
        Songs.selectAll().map { toSong(it) }
    }

    fun findById(id: Int): Song? = transaction {
        Songs.select { Songs.id eq id }
            .map { toSong(it) }
            .singleOrNull()
    }

    fun update(id: Int, song: Song): Boolean = transaction {
        Songs.update({ Songs.id eq id }) {
            it[name] = song.name
            it[album] = song.album
            it[duration] = song.duration
            it[imageUrl] = song.imageUrl
        } > 0
    }

    fun delete(id: Int): Boolean = transaction {
        Songs.deleteWhere { Songs.id eq id } > 0
    }

    private fun toSong(row: ResultRow): Song = Song(
        id = row[Songs.id],
        name = row[Songs.name],
        album = row[Songs.album],
        duration = row[Songs.duration],
        imageUrl = row[Songs.imageUrl]
    )
}