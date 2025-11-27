package com.example.routes

import com.example.models.Song
import com.example.services.SongService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.songRoutes(songService: SongService) {
    route("/songs") {
        post {
            val song = call.receive<Song>()
            val created = songService.createSong(song)
            call.respond(HttpStatusCode.Created, created)
        }

        get {
            val songs = songService.getAllSongs()
            call.respond(songs)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@get
            }
            val song = songService.getSongById(id)
            if (song == null) {
                call.respond(HttpStatusCode.NotFound, "Canción no encontrada")
            } else {
                call.respond(song)
            }
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@put
            }
            val song = call.receive<Song>()
            val updated = songService.updateSong(id, song)
            if (updated) {
                call.respond(HttpStatusCode.OK, "Canción actualizada")
            } else {
                call.respond(HttpStatusCode.NotFound, "Canción no encontrada")
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@delete
            }
            val deleted = songService.deleteSong(id)
            if (deleted) {
                call.respond(HttpStatusCode.OK, "Canción eliminada")
            } else {
                call.respond(HttpStatusCode.NotFound, "Canción no encontrada")
            }
        }
    }
}