package com.udacity.asteroidradar.main

import com.udacity.asteroidradar.module.Asteroid

class AsteroidClickListener(val clickListener: (Asteroid) -> Unit) {
    fun onClickAsteroid(data: Asteroid) = clickListener(data)
}