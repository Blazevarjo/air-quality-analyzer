package com.example.airqualityanalyzer.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "station")
data class Station (
    @PrimaryKey
    val id: Int,
    val stationName: String,
    val gegrLat: Double,
    val gegrLon: Double,
    @Embedded
    val city: City,
    val addressStreet: String?
)

