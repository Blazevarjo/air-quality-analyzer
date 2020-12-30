package com.example.airqualityanalyzer.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "sensor_data",
    foreignKeys = [
        ForeignKey(
            entity = Sensor::class,
            parentColumns = ["Id"],
            childColumns = ["SensorId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SensorData (
    @PrimaryKey(autoGenerate = true)
    val Id: Int,
    val SensorId: Int,
    val Date: Date,
    val value: Double
)