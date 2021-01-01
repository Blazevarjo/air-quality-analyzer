package com.example.airqualityanalyzer.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE


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
    val id: Int,
    val stationId: Int,
    @Embedded
    val param: Param
)