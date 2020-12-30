package com.example.airqualityanalyzer.model

import androidx.room.Embedded
import androidx.room.Entity

@Entity(tableName = "station")
data class Station (
    val Id: Int,
    val StationName: String,
    val GegrLat: Double,
    val GegrLon: Double,
    @Embedded
    val City: City,
    val AddressStreet: String?
)

