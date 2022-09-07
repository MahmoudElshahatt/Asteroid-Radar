package com.udacity.asteroidradar.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.ApiService
import com.udacity.asteroidradar.api.asAsteroidEntities
import com.udacity.asteroidradar.database.AsteroidDataBase
import com.udacity.asteroidradar.database.asAsteroids
import com.udacity.asteroidradar.module.Asteroid
import com.udacity.asteroidradar.module.PictureOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class AsteroidRepository(private val dataBase: AsteroidDataBase) {

    val startDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    @RequiresApi(Build.VERSION_CODES.O)
     val endDate = LocalDateTime.now().minusDays(7).toString()


    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun getAsteroidsFromAPI() {
        withContext(Dispatchers.IO) {
            val asteroids = ApiService.getAsteroids()
            dataBase.databaseDao.insertAsteroids(asteroids.asAsteroidEntities())
        }
    }

    suspend fun getPicture(): PictureOfDay {
        lateinit var pic: PictureOfDay
        withContext(Dispatchers.IO) {
            pic = ApiService.getPic()
        }
        return pic
    }

    val asteroidSavedList: LiveData<List<Asteroid>> =
        Transformations.map(dataBase.databaseDao.getAllAsteroids()) {
            it.asAsteroids()
        }

    @RequiresApi(Build.VERSION_CODES.O)
    val asteroidWeekList: LiveData<List<Asteroid>> = Transformations.map(
        dataBase.databaseDao.getAsteroidsForWeek(
            startDate.toString(),
            endDate
        )
    ) {
        it.asAsteroids()
    }

    val asteroidDayList: LiveData<List<Asteroid>> = Transformations.map(
        dataBase.databaseDao.getAsteroidsForDay(
            startDate.toString()
        )
    ) {
        it.asAsteroids()
    }

}