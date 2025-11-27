package com.example.repositories

import com.example.database.Albums
import com.example.models.Album
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class AlbumRepository {
    fun create(album: Album): Album = transaction {
        val id = Albums.insert {
            it[name] = album.name
            it[artist] = album.artist
            it[year] = album.year
            it[imageUrl] = album.imageUrl
        }[Albums.id]
        album.copy(id = id)
    }

    fun findAll(): List<Album> = transaction {
        Albums.selectAll().map { toAlbum(it) }
    }

    fun findById(id: Int): Album? = transaction {
        Albums.select { Albums.id eq id }
            .map { toAlbum(it) }
            .singleOrNull()
    }

    fun update(id: Int, album: Album): Boolean = transaction {
        Albums.update({ Albums.id eq id }) {
            it[name] = album.name
            it[artist] = album.artist
            it[year] = album.year
            it[imageUrl] = album.imageUrl
        } > 0
    }

    fun delete(id: Int): Boolean = transaction {
        Albums.deleteWhere { Albums.id eq id } > 0
    }

    private fun toAlbum(row: ResultRow): Album = Album(
        id = row[Albums.id],
        name = row[Albums.name],
        artist = row[Albums.artist],
        year = row[Albums.year],
        imageUrl = row[Albums.imageUrl]
    )
}