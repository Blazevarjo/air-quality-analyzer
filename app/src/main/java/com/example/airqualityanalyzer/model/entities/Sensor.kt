package com.example.airqualityanalyzer.model.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey


@Entity(tableName = "sensor",
    foreignKeys = [
        ForeignKey(
            entity = Station::class,
            parentColumns = ["id"],
            childColumns = ["stationId"],
            onDelete = CASCADE
        )
    ]
)
data class Sensor (
    @PrimaryKey
    val id: Int,
    val stationId: Int,
    @Embedded
    val param: Param
)