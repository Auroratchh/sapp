package com.example.routes

import com.example.models.Artist
import com.example.services.ArtistService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.artistRoutes(artistService: ArtistService) {
    route("/artists") {
        post {
            val artist = call.receive<Artist>()
            val created = artistService.createArtist(artist)
            call.respond(HttpStatusCode.Created, created)
        }

        get {
            val artists = artistService.getAllArtists()
            call.respond(artists)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@get
            }
            val artist = artistService.getArtistById(id)
            if (artist == null) {
                call.respond(HttpStatusCode.NotFound, "Artista no encontrado")
            } else {
                call.respond(artist)
            }
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@put
            }
            val artist = call.receive<Artist>()
            val updated = artistService.updateArtist(id, artist)
            if (updated) {
                call.respond(HttpStatusCode.OK, "Artista actualizado")
            } else {
                call.respond(HttpStatusCode.NotFound, "Artista no encontrado")
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@delete
            }
            val deleted = artistService.deleteArtist(id)
            if (deleted) {
                call.respond(HttpStatusCode.OK, "Artista eliminado")
            } else {
                call.respond(HttpStatusCode.NotFound, "Artista no encontrado")
            }
        }
    }
}