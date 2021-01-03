package com.example.airqualityanalyzer.model

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class City (
    @ColumnInfo(name = "cityId")
    val id: Int,
    val name: String?,
    @Embedded
    val commune: Commune
)