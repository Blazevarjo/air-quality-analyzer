package com.example.airqualityanalyzer.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.airqualityanalyzer.model.daoInterfaces.SensorDao
import com.example.airqualityanalyzer.model.daoInterfaces.SensorDataDao
import com.example.airqualityanalyzer.model.daoInterfaces.StationDao
import com.example.airqualityanalyzer.model.entities.Sensor
import com.example.airqualityanalyzer.model.entities.SensorData
import com.example.airqualityanalyzer.model.entities.Station
import com.example.airqualityanalyzer.utils.Converters

@Database(entities = [Station::class, Sensor::class, SensorData::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun stationDao(): StationDao
    abstract fun sensorDao(): SensorDao
    abstract fun sensorDataDao(): SensorDataDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE

            if(tempInstance != null) {
                return tempInstance
            }
            else {
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    ).build()
                    INSTANCE = instance
                    return instance
                }
            }
        }
    }

}