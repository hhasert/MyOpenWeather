package com.example.myopenweather.data

import androidx.room.Dao
import kotlinx.coroutines.flow.Flow
class OfflineItemsRepository(private val locationDao: LocationDao) : LocationsRepository {
    override fun getAllLocationsStream(): Flow<List<LocationData>> = locationDao.getAllItems()

    override fun getLocationStream(id: Int): Flow<LocationData?> = locationDao.getItem(id)

    override suspend fun insertLocation(location: LocationData) = locationDao.insert(location)

    override suspend fun deleteLocation(location: LocationData) = locationDao.delete(location)

    override suspend fun updateLocation(location: LocationData) = locationDao.update(location)
}