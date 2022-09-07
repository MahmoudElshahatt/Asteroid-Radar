package com.udacity.asteroidradar.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Asteroid_entity")
class AsteroidEntity(
    @PrimaryKey
    var id: Long,
    @ColumnInfo(name = "codeName")
    var codeName: String,
    @ColumnInfo(name = "closeApproachDate")
    var closeApproachDate: String,
    @ColumnInfo(name = "absoluteMagnitude")
    var absoluteMagnitude: Double,
    @ColumnInfo(name = "estimatedDiameter")
    var estimatedDiameter: Double,
    @ColumnInfo(name = "relativeVelocity")
    val relativeVelocity: Double,
    @ColumnInfo(name = "distanceFromEarth")
    val distanceFromEarth: Double,
    @ColumnInfo(name = "isPotentiallyHazardous")
    val isPotentiallyHazardous: Boolean
)
