package com.example.myopenweather.data

import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [LocationData] from a given data source.
 */
interface LocationsRepository {
    /**
     * Retrieve all the items from the given data source.
     */
    fun getAllLocationsStream(): Flow<List<LocationData>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getLocationStream(id: Int): Flow<LocationData?>

    /**
     * Insert item in the data source
     */
    suspend fun insertLocation(location: LocationData)

    /**
     * Delete item from the data source
     */
    suspend fun deleteLocation(location: LocationData)

    /**
     * Update item in the data source
     */
    suspend fun updateLocation(location: LocationData)
}