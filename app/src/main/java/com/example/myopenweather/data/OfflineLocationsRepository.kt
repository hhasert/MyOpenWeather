package com.example.myopenweather.data

import androidx.room.Dao
import kotlinx.coroutines.flow.Flow
class OfflineItemsRepository(private val locationDao: LocationsDao) : LocationsRepository {
    override fun getAllItemsStream(): Flow<List<LocationData>> = locationDao.getAllItems()

    override fun getItemStream(id: Int): Flow<LocationData?> = locationDao.getItem(id)

    override suspend fun insertItem(location: LocationData) = locationDao.insert(location)

    override suspend fun deleteItem(location: LocationData) = locationDao.delete(location)

    override suspend fun updateItem(location: LocationData) = locationDao.update(location)
}