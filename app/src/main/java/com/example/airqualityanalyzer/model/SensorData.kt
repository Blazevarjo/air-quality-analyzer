package com.example.airqualityanalyzer.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "sensor_data",
    foreignKeys = [
        ForeignKey(
            entity = Sensor::class,
            parentColumns = ["id"],
            childColumns = ["sensorId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SensorData (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val sensorId: Int,
    val date: Date,
    val value: Double
)