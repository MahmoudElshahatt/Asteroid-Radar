package com.udacity.asteroidradar.api

import android.os.Build
import androidx.annotation.RequiresApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.module.Asteroid
import com.udacity.asteroidradar.utils.Constants
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .build()

object ApiService {
    val retrofitService: AsteroidService by lazy {
        retrofit.create(AsteroidService::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun getAsteroids(): List<Asteroid> {
        val response = retrofitService.getAsteroids("", "", Constants.API_KEY)
        val jsonObj = JSONObject(response)
        return parseAsteroidsJsonResult(jsonObj)

    }

    suspend fun getPic() = retrofitService.getPic(Constants.API_KEY)
}