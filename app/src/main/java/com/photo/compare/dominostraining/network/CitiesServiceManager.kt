package com.photo.compare.dominostraining.network

import com.photo.compare.dominostraining.model.City
import retrofit2.Call
import retrofit2.http.GET

interface CitiesServiceManager {
    @GET("/libinbensin/ddd611c372876180f599fb921be22c00/raw/6fdf64cec284da5e8759688dd47f4dd1c1720570/cities.json")
    fun getCities() : Call<List<City>>
}