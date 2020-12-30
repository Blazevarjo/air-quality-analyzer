package com.example.airqualityanalyzer.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE


@Entity(tableName = "sensor",
    foreignKeys = [
        ForeignKey(
            entity = Station::class,
            parentColumns = ["Id"],
            childColumns = ["StationId"],
            onDelete = CASCADE
        )
    ]
)
data class Sensor (
    val Id: Int,
    val StationId: Int,
    @Embedded
    val Param: Param
)