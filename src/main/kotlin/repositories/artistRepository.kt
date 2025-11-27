package com.example.repositories

import com.example.database.Artists
import com.example.models.Artist
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update


class ArtistRepository {
    fun create(artist: Artist): Artist = transaction {
        val id = Artists.insert {
            it[name] = artist.name
            it[imageUrl] = artist.imageUrl
        }[Artists.id]
        artist.copy(id = id)
    }

    fun findAll(): List<Artist> = transaction {
        Artists.selectAll().map { toArtist(it) }
    }

    fun findById(id: Int): Artist? = transaction {
        Artists.select { Artists.id eq id }
            .map { toArtist(it) }
            .singleOrNull()
    }

    fun update(id: Int, artist: Artist): Boolean = transaction {
        Artists.update({ Artists.id eq id }) {
            it[name] = artist.name
            it[imageUrl] = artist.imageUrl
        } > 0
    }

    fun delete(id: Int): Boolean = transaction {
        Artists.deleteWhere { Artists.id eq id } > 0
    }

    private fun toArtist(row: ResultRow): Artist = Artist(
        id = row[Artists.id],
        name = row[Artists.name],
        imageUrl = row[Artists.imageUrl]
    )
}