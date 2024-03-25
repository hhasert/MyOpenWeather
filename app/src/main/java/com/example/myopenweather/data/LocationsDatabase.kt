package com.example.myopenweather.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [LocationData::class], version = 1, exportSchema = false)
abstract class LocationsDatabase : RoomDatabase() {
    abstract fun locationsDao(): LocationsDao
    companion object {
        @Volatile
        private var Instance: LocationsDatabase? = null

        fun getDatabase(context: Context): LocationsDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, LocationsDatabase::class.java, "locations_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}