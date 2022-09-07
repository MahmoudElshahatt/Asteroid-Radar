package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAsteroids(asteroids: List<AsteroidEntity>)

    @Query("SELECT * FROM Asteroid_entity ORDER BY closeApproachDate DESC")
    fun getAllAsteroids(): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM Asteroid_entity WHERE closeApproachDate BETWEEN :afterWeekDate AND :todayDate  ORDER BY closeApproachDate DESC")
    fun getAsteroidsForWeek(todayDate : String, afterWeekDate : String):LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM Asteroid_entity WHERE closeApproachDate = :startDate ORDER BY closeApproachDate DESC")
    fun getAsteroidsForDay(startDate: String): LiveData<List<AsteroidEntity>>
}