package com.udacity.asteroidradar.database

import com.udacity.asteroidradar.module.Asteroid

fun List<AsteroidEntity>.asAsteroids():List<Asteroid>{
    return map{
        Asteroid(
            id=it.id,
            codename = it.codeName,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }

}