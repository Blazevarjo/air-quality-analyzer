package com.example.airqualityanalyzer.model

import androidx.room.Embedded

data class City (
    val Id: Int,
    val Name: String,
    @Embedded
    val Commune: Commune
)