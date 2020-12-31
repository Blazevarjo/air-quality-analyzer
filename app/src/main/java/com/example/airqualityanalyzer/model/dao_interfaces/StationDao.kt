package com.example.airqualityanalyzer.model.dao_interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.airqualityanalyzer.model.Station

@Dao
interface StationDao {

    @Insert
    suspend fun addStation(station: Station)

    @Delete
    suspend fun deleteStation(station: Station)

    @Query("SELECT * FROM station")
    fun allStations(): List<Station>

}