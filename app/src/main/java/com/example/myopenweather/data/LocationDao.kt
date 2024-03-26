package com.example.myopenweather.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(location: LocationData)

    @Update
    suspend fun update(location: LocationData)

    @Delete
    suspend fun delete(location: LocationData)

// TODO implement the queries needed - these are examples
    @Query("SELECT * from locations WHERE id = :id")
    fun getItem(id: Int): Flow<LocationData>

    @Query("SELECT * from locations ORDER BY name ASC")
    fun getAllItems(): Flow<List<LocationData>>

}