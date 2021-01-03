package com.example.airqualityanalyzer.model.daoInterfaces

import androidx.lifecycle.LiveData
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
    fun allStations(): LiveData<List<Station>>

}