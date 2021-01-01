package com.example.airqualityanalyzer.model

import androidx.room.Embedded

data class City (
    val id: Int,
    val name: String,
    @Embedded
    val commune: Commune
)