package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.udacity.asteroidradar.database.AsteroidDataBase
import com.udacity.asteroidradar.module.Asteroid
import com.udacity.asteroidradar.module.PictureOfDay
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val _navigateToDetailFragment = MutableLiveData<Asteroid?>()
    val navigateToDetailFragment
        get() = _navigateToDetailFragment

    private val database = AsteroidDataBase.getInstance(app)
    private val repository = AsteroidRepository(database)

    private val timeFilter = MutableLiveData(AsteroidTime.ALL)

    @RequiresApi(Build.VERSION_CODES.O)
    val asteroids =
        Transformations.switchMap(timeFilter) {
            when (it) {
                AsteroidTime.WEEK -> repository.asteroidWeekList
                AsteroidTime.TODAY -> repository.asteroidDayList
                else -> repository.asteroidSavedList
            }
        }

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    init {
        refresh()
        pic()
        _navigateToDetailFragment.value=null
    }

    fun onChangeFilter(_filter: AsteroidTime) {
        timeFilter.postValue(_filter)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun refresh() {
        viewModelScope.launch {
            try {
                repository.getAsteroidsFromAPI()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun pic() {
        viewModelScope.launch {
            try {
                _pictureOfDay.value = repository.getPicture()
            } catch (e: Exception) {
            }
        }
    }

}