package com.example.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

object Artists : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val imageUrl = varchar("image_url", 500)
    override val primaryKey = PrimaryKey(id)
}

object Albums : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val artist = varchar("artist", 255)
    val year = integer("year")
    val imageUrl = varchar("image_url", 500)
    override val primaryKey = PrimaryKey(id)
}

object Songs : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val album = varchar("album", 255)
    val duration = varchar("duration", 10)
    val imageUrl = varchar("image_url", 500)
    override val primaryKey = PrimaryKey(id)
}

object DatabaseFactory {
    fun init() {
        val config = HikariConfig().apply {
            jdbcUrl = "jdbc:postgresql://localhost:5432/musicback_db"
            driverClassName = "org.postgresql.Driver"
            username = "postgres"
            password = "aurora28"
            maximumPoolSize = 10
        }

        val dataSource = HikariDataSource(config)
        Database.connect(dataSource)

        transaction {
            SchemaUtils.create(Artists, Albums, Songs)
        }
    }
}