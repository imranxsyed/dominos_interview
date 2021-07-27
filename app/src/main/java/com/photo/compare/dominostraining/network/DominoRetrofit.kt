package com.photo.compare.dominostraining.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DominoRetrofit {
    fun getRetrofitClient(baseUrl : String) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}